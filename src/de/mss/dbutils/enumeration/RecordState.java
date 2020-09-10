package de.mss.dbutils.enumeration;

import de.mss.utils.exception.MssException;

public enum RecordState {
   //@Formatter:off
   INSERTED          ("I"),
   STARTED           ("S"),
   WAITING           ("W"),
   ACTIVE            ("A"),
   PROCESSING        ("P"),
   FINISHED          ("F"),
   ERROR             ("E"),
   DELETED           ("D"),
   INACTIVE          ("X"),
   MANUALLY_DELETED  ("Z")
   ;
   //@formatter:on
   
   private String dbChar = null;
   
   private RecordState (String s) {
      this.dbChar = s;
   }
   
   
   public String getDbChar() {
      return this.dbChar;
   }
   
   
   public static RecordState getByDbChar(String d) throws MssException {
      for (RecordState s : RecordState.values()) {
         if (s.getDbChar().equals(d))
            return s;
      }
      
      throw new MssException(de.mss.dbutils.exception.ErrorCodes.ERROR_UNKNOWN_RECORD_STATE, "RecordState '" + d + "' unknown");
   }
}