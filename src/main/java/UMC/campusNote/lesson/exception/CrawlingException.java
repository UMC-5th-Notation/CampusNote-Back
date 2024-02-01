package UMC.campusNote.lesson.exception;

import UMC.campusNote.common.code.BaseErrorCode;
import UMC.campusNote.common.exception.GeneralException;
import lombok.Getter;

@Getter
public class CrawlingException extends GeneralException {
    public CrawlingException(BaseErrorCode code) {
        super(code);
    }
}
