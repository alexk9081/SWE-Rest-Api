package edu.swe.group10.restapi.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import edu.swe.group10.restapi.AppLogger;
import edu.swe.group10.restapi.DatabaseConnection;
import edu.swe.group10.restapi.API.Model.Notecard;
import edu.swe.group10.restapi.API.Model.NotecardSet;
import edu.swe.group10.restapi.API.Model.User;

/**
 * Manages the creation, editing, deleting, and getting of notecard set information.
 */
@Service
public class NotecardSetService {

	private Logger logger;
	private Connection conn;

	public NotecardSetService() {
		logger = AppLogger.getInstance().getLogger();
		conn = DatabaseConnection.getInstance().getConnection();
	}

	/**
	 * Creates notecard set.
	 * 
	 * @param setID
	 *          the unique setID of the notecard set
	 * @param name
	 *          the name of the notecard set
	 * @param nNumber
	 *          the user's nNumber
	 * @return
	 */
	public int createNotecardSet(String id, String name, String nNumber, String description, boolean isPublic) {
		logger.info("Creating notecard set for {} with name {}, description {}, isPublic {}, and id {}", nNumber, name,
				description, isPublic, id);

		String isPublicString = isPublic ? "T" : "F";

		try {
			PreparedStatement pstmt = conn
					.prepareStatement("INSERT INTO NOTECARD_SET (Set_ID, Set_Name, N_Number, Set_Description, Is_Public) "
							+ " VALUES (?, ?, ?, ?, ?)");
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			pstmt.setString(3, nNumber);
			pstmt.setString(4, description);
			pstmt.setString(5, isPublicString);

			int rows = pstmt.executeUpdate();
			logger.info("Rows inserted: {}", rows);

			return rows > 0 ? 0 : 1;
		} catch (SQLException e) {
			logger.error("Creating notecard set was unsucessful.");
			e.printStackTrace();

			return 2;
		}
	}

	/**
	 * Uses set ID and nNumber to return a single notecard set object. Returns null if none found.
	 * 
	 * If set has no notecard, notecard variable will be null instead of an arraylist.
	 * 
	 * @param id
	 *          the setID of the notecard set
	 * @param nNumber
	 *          the nNumber of a user
	 * @return notecardSet the instance of a notecardSet, will return null if none was found.
	 */
	public NotecardSet getNotecardSet(String id, String nNumber) {
		logger.info("Getting notecard set {} by user {}", id, nNumber);

		NotecardSet set = null;
		List<Notecard> notecards;

		String studentNum = null;
		String setID = null;
		String setName = null;
		String setDescription = null;
		boolean isPublic = false; // unless set to true
		String userName = null;
		String userImg = null;

		/**
		 * Step 1: get basic information of set
		 */
		try {
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM NOTECARD_SET LEFT JOIN student ON student.n_number = notecard_set.n_number WHERE Set_ID = ? AND notecard_set.n_number = ?");

			pstmt.setString(1, id);
			pstmt.setString(2, nNumber);

			ResultSet result = pstmt.executeQuery();

			if (result.next()) {
				studentNum = result.getString("N_Number");
				setID = result.getString("Set_ID");
				setName = result.getString("Set_Name");
				setDescription = result.getString("Set_Description");
				isPublic = result.getString("Is_Public").equals("T");
				userName = result.getString("name");
				userImg = result.getString("image_url");
			}
		} catch (SQLException e) {
			logger.error("Getting notecard set was unsucessful. Specifically getting the notecard set's information.");
			e.printStackTrace();
		}

		/*
		 * Step 2: Get notecards for corresponding set.
		 */

		notecards = getAllNotecardsOfSet(id);

		/*
		 * Step 3: Now we have the set information and the notecards the set has, we have to create the set and return it
		 */

		try {
			set = new NotecardSet(setID, setName, isPublic, studentNum, setDescription, notecards, new User(nNumber, userName, userImg));
		} catch (Exception e) {
			logger.error("Creating notecard set object unsuccessful. Most likely set does not exist.");
			e.printStackTrace();
		}

		return set;
	}

	/**
	 * Returns arraylist of notecard set objects that are public.
	 * 
	 * @return publicSets an array list of sets that are public
	 */
	public List<NotecardSet> getPublicNotecardSets() {
		logger.info("Getting all public notecard sets");
		List<NotecardSet> publicSets = new ArrayList<>();

		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"SELECT * FROM NOTECARD_SET LEFT JOIN student ON student.n_number = notecard_set.n_number WHERE is_public = 'T'");

			logger.info("Querying all public notecard sets");
			ResultSet result = pstmt.executeQuery();

			while (result.next()) {
				NotecardSet currentSet = null;
				List<Notecard> currentNotecards = new ArrayList<>();

				String studentNum = result.getString("N_Number");
				String setID = result.getString("Set_ID");
				String setName = result.getString("Set_Name");
				String setDescription = result.getString("Set_Description");
				boolean isPublic = result.getString("Is_Public").equals("T");
				String userName = result.getString("name");
				String userNNumber = result.getString("n_number");
				String userImg = result.getString("image_url");
				// currentNotecards = getAllNotecardsOfSet(setID);

				currentSet = new NotecardSet(setID, setName, isPublic, studentNum, setDescription, currentNotecards,
						new User(userNNumber, userName, userImg));

				publicSets.add(currentSet);
			}
		} catch (SQLException e) {
			logger.error("Getting all public sets was unsuccessful.");
			e.printStackTrace();
		}

		logger.info("{} public sets found", publicSets.size());

		return publicSets;
	}

	/**
	 * 
	 * Returns arraylist of notecard set objects that are the user's.
	 * 
	 * @return all an array list of sets that are public
	 */
	public List<NotecardSet> getUsersNotecardSets(String nNumber) {
		logger.info("Getting all notecard sets for user {}", nNumber);
		List<NotecardSet> usersSets = new ArrayList<>();

		try {
			PreparedStatement pstmt = conn.prepareStatement(
					"SELECT * FROM NOTECARD_SET LEFT JOIN student ON student.n_number = notecard_set.n_number WHERE notecard_set.n_Number = ?");

			pstmt.setString(1, nNumber);

			ResultSet result = pstmt.executeQuery();

			while (result.next()) {
				NotecardSet currentSet = null;
				List<Notecard> currentNotecards = new ArrayList<>();

				String studentNum = result.getString("N_Number");
				String setID = result.getString("Set_ID");
				String setName = result.getString("Set_Name");
				String setDescription = result.getString("Set_Description");
				boolean isPublic = result.getString("Is_Public").equals("T");
				String userName = result.getString("name");
				String userImg = result.getString("image_url");

				// currentNotecards = getAllNotecardsOfSet(setID);

				currentSet = new NotecardSet(setID, setName, isPublic, studentNum, setDescription, currentNotecards,
						new User(nNumber, userName, userImg));

				usersSets.add(currentSet);
			}
		} catch (SQLException e) {
			logger.error("Getting all public sets was unsuccessful.");
			e.printStackTrace();
		}

		return usersSets;
	}

	/**
	 * Uses set ID to delete a single notecard set object.
	 * 
	 * @param id
	 *          the setID of the set
	 * @return
	 */
	public int deleteNotecardSet(String id) {
		logger.info("Deleting notecard with id {}", id);
		try {
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM NOTECARD_SET " + "WHERE Set_ID = ?");

			pstmt.setString(1, id);

			int rows = pstmt.executeUpdate();
			logger.info("Rows deleted: {}", rows);

			return rows > 0 ? 0 : 1;
		} catch (SQLException e) {
			logger.error("Deleting notecard set was unsucessful.");
			e.printStackTrace();

			return 2;
		}

	}

	/**
	 * Uses set ID and nNumber to update a single notecard set object.
	 * 
	 * Provide all variables, even if one remains the same. If you don't it'll be set to null.
	 * 
	 * @param isPublic
	 *          whether or not the set is public
	 * @param name
	 *          the set's name
	 * @param description
	 *          the set's description
	 * @param nNumber
	 *          the set's creator's nNumber
	 * @param id
	 *          the set's ID
	 */
	public void updateNotecardSet(boolean isPublic, String name, String description, String nNumber, String id) {
		logger.info("Updating notecard with id {}", id);

		String isPublicString = isPublic ? "T" : "F";

		try {
			PreparedStatement pstmt = conn.prepareStatement("UPDATE NOTECARD_SET "
					+ "SET Is_Public = ?, Set_Name = ?, Set_Description = ?" + " WHERE N_Number = ? AND Set_ID = ? ");

			pstmt.setString(1, isPublicString);
			pstmt.setString(2, name);
			pstmt.setString(3, description);
			pstmt.setString(4, nNumber);
			pstmt.setString(5, id);

			int rows = pstmt.executeUpdate();
			logger.info("Rows updated: {}", rows);
		} catch (SQLException e) {
			logger.error("Updating notecard set was unsucessful.");
			e.printStackTrace();
		}
	}

	/**
	 * Gets all notecards from provided set_id
	 */
	private List<Notecard> getAllNotecardsOfSet(String setID) {
		logger.info("Getting all notecards for set {}", setID);

		Notecard currentNotecard = null;
		List<Notecard> notecards = new ArrayList<>();

		try {
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM NOTECARD WHERE Set_ID = ?");

			pstmt.setString(1, setID);

			ResultSet result = pstmt.executeQuery();

			String[] notecardSetArr = new String[4];

			while (result.next()) {
				notecardSetArr[0] = result.getString("set_id");
				notecardSetArr[1] = result.getString("notecard_id");
				notecardSetArr[2] = result.getString("question");
				notecardSetArr[3] = result.getString("answer");

				// Constructor's Order: notecard_id, question, answer, set_id

				currentNotecard = new Notecard(notecardSetArr[1], notecardSetArr[2], notecardSetArr[3], notecardSetArr[0]);

				notecards.add(currentNotecard);
			}

		} catch (SQLException e) {
			logger.error("Getting notecard set was unsucessful. Specifically checking or getting the notecards in the set.");
			e.printStackTrace();
		}

		return notecards;
	}
}
