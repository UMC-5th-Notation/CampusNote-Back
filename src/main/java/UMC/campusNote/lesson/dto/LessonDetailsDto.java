package UMC.campusNote.lesson.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class LessonDetailsDto {
    private String professorName; // 교수명
    private String location; // 강의실

    private String startTime;
    private String runningTime;
    private String dayOfWeek; // 요일
}
