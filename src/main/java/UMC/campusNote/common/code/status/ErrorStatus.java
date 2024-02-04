package UMC.campusNote.common.code.status;

import UMC.campusNote.common.code.BaseErrorCode;
import UMC.campusNote.common.code.ErrorReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorStatus implements BaseErrorCode {

    // 가장 일반적인 응답
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),
    BAD_REQUEST(HttpStatus.BAD_REQUEST,"COMMON400","잘못된 요청입니다."),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"COMMON401","로그인 인증이 필요합니다."),
    FORBIDDEN(HttpStatus.FORBIDDEN, "COMMON403", "금지된 요청입니다."),

    // 레슨 관련 에러
    LESSON_NOT_FOUND(HttpStatus.BAD_REQUEST, "LESSON4001","존재하지 않는 수업."),

    // 크롤링 관련 에러
    CRAWLING_URL_INVALID(HttpStatus.BAD_REQUEST, "URL4001", "적절하지 않은 url."),
    CRAWLING_URL_BINDING_FAULT(HttpStatus.BAD_REQUEST, "URL4002", "파라미터 바인딩 실패."),

    // 멤버 관려 에러
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "MEMBER4001", "사용자가 없습니다."),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "MEMBER4002", "비밀번호가 일치하지 않습니다."),
    PASSWORD_NOT_MATCH_CONFIRM(HttpStatus.BAD_REQUEST, "MEMBER4003", "새비밀번호와 재입력한 새비밀번호가 일치하지 않습니다."),
    USER_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "MEMBER4004", "이미 존재하는 사용자입니다."),

    // 토큰 관련 에러
    TOKEN_NOT_FOUND(HttpStatus.BAD_REQUEST, "TOKEN4001", "토큰이 존재하지 않습니다."),
    TOKEN_EXPIRED(HttpStatus.BAD_REQUEST, "TOKEN4002", "토큰이 만료되었습니다."),
    TOKEN_MALFORM(HttpStatus.BAD_REQUEST, "TOKEN4003", "토큰이 변조되었습니다."),
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
