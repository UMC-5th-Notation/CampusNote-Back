package UMC.campusNote.page.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

public class PageRequestDTO {
    @Getter
    public static class PageDTO {
        @Schema(description = "노트아이디", example = "1")
        Long noteId;

        @Schema(description = "페이지 번호", example = "1")
        Integer pageNum;

        @Schema(description = "사이드노트(텍스트)", example = "사이드노트입니다.")
        String sideNote;

        @Schema(description = "회차", example = "1")
        Integer round;
    }
}
