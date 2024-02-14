package UMC.campusNote.lesson.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

public class LessonRequestDTO {
    @AllArgsConstructor
    @Getter
    @Setter
    @NoArgsConstructor
    @Builder
    public static class CreateDTO {
        @Schema(description = "사용자의 학기", example = "3-1")
        @NotNull
        private String attendedSemester;
        @NotNull
        private String lessonName;
        @Schema(description = "학교의 학기", example = "2024-1")
        @NotNull
        private String semester;
        @NotNull
        private String professorName;
        @NotNull
        private String location;
        @NotNull
        private String startTime;
        @NotNull
        private String runningTime;
        @NotNull
        private String dayOfWeek;
    }

    @AllArgsConstructor
    @Getter
    @Setter
    @NoArgsConstructor
    public static class CrawlingDTO {
        @NotNull
        private String url;
    }

}
