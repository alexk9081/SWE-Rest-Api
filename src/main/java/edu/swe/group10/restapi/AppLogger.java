package edu.swe.group10.restapi;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AppLogger {
  public Logger logger;

  private static AppLogger instance;

  private AppLogger(){
    logger = LoggerFactory.getLogger(AppLogger.class);
  }

  // static block initialization for exception handling
  static {
    try {
      instance = new AppLogger();
    } catch (Exception e) {
      throw new NullPointerException("Exception occurred in creating singleton instance");
    }
  }

  public static AppLogger getInstance() {
    return instance;
  }
}
