package UMC.campusNote.lesson.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

public class LessonRequestDTO {
    @AllArgsConstructor
    @Getter
    @Setter
    @NoArgsConstructor
    @Builder
    public static class CreateDTO {
        @NotNull
        private String attendedSemester;
        @NotNull
        private String lessonName;
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
