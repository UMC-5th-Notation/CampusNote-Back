package UMC.campusNote.common.exception.handler;

import UMC.campusNote.common.code.BaseErrorCode;
import UMC.campusNote.common.exception.GeneralException;

public class ExceptionHandler extends GeneralException {
    public ExceptionHandler(BaseErrorCode errorCode) {super(errorCode);}
}
