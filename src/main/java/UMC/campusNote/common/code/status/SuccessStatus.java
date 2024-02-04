package UMC.campusNote.common.code.status;

import UMC.campusNote.common.code.BaseCode;
import UMC.campusNote.common.code.ReasonDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SuccessStatus implements BaseCode {

    // 일반적인 응답
    OK(HttpStatus.OK, "COMMON200", "성공입니다."),
    TEST(HttpStatus.ACCEPTED,"1231","test"),

    // 크롤링
    CRAWLING_OK(HttpStatus.CREATED, "CRAWL201", "크롤링 성공"),

    // 수업
    LESSON_CREATE(HttpStatus.CREATED, "LESSON201", "수업 생성 성공"),

    // 회원가입
    USER_JOIN(HttpStatus.CREATED, "USER200", "회원가입 성공"),
    USER_LOGIN(HttpStatus.OK, "USER201", "로그인 성공"),
    USER_LOGOUT(HttpStatus.OK, "USER202", "로그아웃 성공"),

    ACCESS_TOKEN(HttpStatus.CREATED, "USER203", "토큰 재발급 성공");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

    @Override
    public ReasonDTO getReason() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .build();
    }

    @Override
    public ReasonDTO getReasonHttpStatus() {
        return ReasonDTO.builder()
                .message(message)
                .code(code)
                .isSuccess(true)
                .httpStatus(httpStatus)
                .build()
                ;
    }
}