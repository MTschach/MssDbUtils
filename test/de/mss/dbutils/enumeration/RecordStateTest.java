package de.mss.dbutils.enumeration;

import org.junit.Test;

import de.mss.utils.exception.MssException;
import junit.framework.TestCase;

public class RecordStateTest extends TestCase {
   /*
    *    INSERTED    ("I"),
   STARTED     ("S"),
   WAITING     ("W"),
   ACTIVE      ("A"),
   PROCESSING  ("P"),
   FINISHED    ("F"),
   ERROR       ("E"),
   DELETED     ("D"),
   INACTIVE    ("X")
   
    */

   @Test
   public void testGetByDbChar() throws MssException {
      checkByDbChar("I", RecordState.INSERTED);
      checkByDbChar("S", RecordState.STARTED);
      checkByDbChar("W", RecordState.WAITING);
      checkByDbChar("A", RecordState.ACTIVE);
      checkByDbChar("P", RecordState.PROCESSING);
      checkByDbChar("F", RecordState.FINISHED);
      checkByDbChar("E", RecordState.ERROR);
      checkByDbChar("D", RecordState.DELETED);
      checkByDbChar("X", RecordState.INACTIVE);
      checkByDbChar("Z", RecordState.MANUALLY_DELETED);
   }


   @Test
   public void testUnknown() {
      checkUnknown(null);
      checkUnknown("");
      checkUnknown("lala");
   }


   private void checkUnknown(String s) {
      try {
         RecordState.getByDbChar(s);
         fail();
      }
      catch (MssException e) {
         assertNotNull(e.getError());
         assertEquals("ErrorCode", de.mss.dbutils.exception.ErrorCodes.ERROR_UNKNOWN_RECORD_STATE.getErrorCode(), e.getError().getErrorCode());
      }
   }


   private void checkByDbChar(String s, RecordState expectedRecordState) throws MssException {
      RecordState r = RecordState.getByDbChar(s);
      
      assertEquals("State", expectedRecordState.getDbChar(), r.getDbChar());
   }

}
