package de.mss.dbutils.exception;

import java.io.Serializable;

import de.mss.utils.exception.Error;
import de.mss.utils.exception.MssException;

public class ErrorCodes implements Serializable {

   private static final long serialVersionUID                 = 4521372659626589761L;

   private static final int  ERROR_CODE_BASE                  = 5000;
   public static final Error ERROR_UNKNOWN_RECORD_STATE       = new Error(ERROR_CODE_BASE + 0, "unknown record state");
   public static final Error ERROR_NON_NULLABLE_VALUE_IS_NULL = new Error(ERROR_CODE_BASE + 1, "non nullable value is null");
   public static final Error ERROR_SET_LIST_IS_EMPTY          = new Error(ERROR_CODE_BASE + 2, "the list for setting values is null or empty");
   public static final Error ERROR_WRONG_TYPE                 = new Error(ERROR_CODE_BASE + 3, "type does not match value");
   public static final Error ERROR_PARAM_COUNT_DOES_NOT_MATCH = new Error(ERROR_CODE_BASE + 4, "number of params and types does not match");
   public static final Error ERROR_CLOSING_CONNECTION         = new Error(ERROR_CODE_BASE + 5, "close connection failed");
   public static final Error ERROR_PREPARING_STATEMENT        = new Error(ERROR_CODE_BASE + 6, "prepare statement failed");
   public static final Error ERROR_WAITING                    = new Error(ERROR_CODE_BASE + 7, "error while waiting for a while");


   public ErrorCodes() throws MssException {
      throw new MssException(
            new Error(
                  de.mss.utils.exception.ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorCode(),
                  de.mss.utils.exception.ErrorCodes.ERROR_NOT_INSTANCABLE.getErrorText() + " (" + getClass().getName() + ")"));
   }
}
