package de.mss.dbutils.exception;

import org.junit.Test;

import de.mss.utils.exception.MssException;
import junit.framework.TestCase;

public class ErrorCodesTest extends TestCase {


   @Test
   public void testConstructor() {
      try {
         new ErrorCodes();
      }
      catch (MssException e) {
         assertNotNull(e.getError());
         assertEquals("ErrorCode", de.mss.utils.exception.ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorCode(), e.getError().getErrorCode());
      }
   }
}
