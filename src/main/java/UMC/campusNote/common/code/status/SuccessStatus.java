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

    ACCESS_TOKEN(HttpStatus.CREATED, "USER203", "토큰 재발급 성공"),

    // 오디오
    AUDIO_CREATE(HttpStatus.CREATED, "AUDIO200", "녹음 생성 성공"),
    AUDIO_GET_ALL(HttpStatus.OK, "AUDIO201", "녹음 파일 전체 조회 성공"),
    AUDIO_GET_ONE(HttpStatus.OK, "AUDIO202", "녹음 파일 조회 성공"),
    AUDIO_DELETE(HttpStatus.OK, "AUDIO203", "녹음 파일 삭제 성공"),


    // 노트
    NOTE_CREATE(HttpStatus.CREATED, "NOTE200", "노트 생성 성공"),
    NOTE_GET_ALL(HttpStatus.OK, "NOTE201", "노트 전체 조회 성공"),
    NOTE_GET_ONE(HttpStatus.OK, "NOTE202", "노트 조회 성공"),
    NOTE_UPDATE(HttpStatus.OK, "NOTE203", "노트 수정 성공"),
    NOTE_DELETE(HttpStatus.OK, "NOTE204", "노트 삭제 성공"),

    // 강의노트
    LESSONNOTE_CREATE(HttpStatus.CREATED, "LESSONNOTE201", "강의 노트 업로드 성공"),
    LESSONNOTE_GET(HttpStatus.OK, "LESSONNOTE200", "강의 노트 조회 성공"),
    LESSONNOTE_DELETE(HttpStatus.OK, "LESSONNOTE202", "강의 노트 삭제 성공"),

    // 이미지
    IMAGE_CREATE(HttpStatus.CREATED, "IMAGE201", "이미지 업로드 성공"),
    IMAGE_GET_ONE(HttpStatus.OK, "IMAGE200", "노트의 특정 이미지 조회 성공"),
    IMAGE_GET_ALL(HttpStatus.OK, "IMAGE200", "노트의 모든 이미지 조회 성공"),
    IMAGE_DELETE_ONE(HttpStatus.OK, "IMAGE202", "노트의 특정 이미지 삭제 성공"),
    IMAGE_DELETE_ALL(HttpStatus.OK, "IMAGE202", "노트의 모든 이미지 삭제 성공"),
    ;

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