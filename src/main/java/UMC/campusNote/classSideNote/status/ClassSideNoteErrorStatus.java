package UMC.campusNote.classSideNote.status;

import UMC.campusNote.common.code.BaseErrorCode;
import UMC.campusNote.common.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ClassSideNoteErrorStatus implements BaseErrorCode {
    // user lesson 관련
    USER_LESSON_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_LESSON4001", "회원에게 없는 수업입니다."),

    // class side note 관련
    CLASS_SIDE_NOTE_NOT_FOUND(HttpStatus.NOT_FOUND,"CLASS_SIDE_NOTE_4001","없는 사이드 노트입니다."),
    CLASS_SIDE_NOTE_BAD_REQUEST(HttpStatus.BAD_REQUEST, "CLASS_SIDE_NOTE4002", "잘못된 요청입니다."),
    CLASS_SIDE_NOTE_BAD_DEADLINE(HttpStatus.BAD_REQUEST, "CLASS_SIDE_NOTE4003",
            "todo 이면서 deadline 이 null 일 수 없습니다."),
    CLASS_SIDE_NOTE_BAD_COLOR_CODE(HttpStatus.BAD_REQUEST, "CLASS_SIDE_NOTE4004",
            "잘못된 Color Code 입니다."),

    ;

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ErrorReasonDTO getReason() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .build();
    }

    @Override
    public ErrorReasonDTO getReasonHttpStatus() {
        return ErrorReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(false)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}
