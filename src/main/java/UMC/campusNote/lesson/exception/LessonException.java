package UMC.campusNote.lesson.exception;

import UMC.campusNote.common.code.BaseErrorCode;
import UMC.campusNote.common.exception.GeneralException;
import lombok.Getter;

@Getter
public class LessonException extends GeneralException {
    public LessonException(BaseErrorCode code) {
        super(code);
    }
}
