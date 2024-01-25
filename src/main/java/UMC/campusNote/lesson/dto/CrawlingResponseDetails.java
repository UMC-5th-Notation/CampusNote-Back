package UMC.campusNote.lesson.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class CrawlingResponseDetails {
    private String professorName; // 교수명
    private String location; // 강의실

    private double startTime;
    private double runningTime;
    private String dayOfWeek; // 요일
}
