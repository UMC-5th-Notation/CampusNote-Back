package UMC.campusNote.page.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class PageResponseDTO {
    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class pageResultDTO{
        Long noteId;
        Long pageId;
    }

    @Builder
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class GetpageResultDTO{
        Long pageId;
        String handWritingSVG;
        Integer pageNumber;
        String sideNote;
    }
}
