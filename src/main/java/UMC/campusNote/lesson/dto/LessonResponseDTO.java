package UMC.campusNote.lesson.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

public class LessonResponseDTO {
    @Data
    @AllArgsConstructor
    @Builder
    public static class FindResultDTO {
        private Long id;
        private String university;
        private String semester;
        private String lessonName;

        @Builder.Default
        private List<FindResultDetailsDTO> findResultDetailsDTOList = new ArrayList<>();
    }

    @Data
    @AllArgsConstructor
    @Builder
    public static class FindResultDetailsDTO {
        private String professorName;
        private String location;
        private String startTime;
        private String runningTime;
        private String dayOfWeek;
    }

}
