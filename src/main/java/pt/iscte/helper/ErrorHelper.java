package pt.iscte.helper;

import java.util.Map;

/**
 * ErrorHelper
 * This class helps generate repetitive structures used in error responses
 */
public class ErrorHelper {

  public static Map<String, String> getErrorEntity(String value){
    return Map.of("Error", value);
  }
}
