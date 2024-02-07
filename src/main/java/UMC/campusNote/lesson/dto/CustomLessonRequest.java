package UMC.campusNote.lesson.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class CustomLessonRequest {
    private String lessonName; // 수업명
    private String semester; // 학기
    private String professorName; // 교수명
    private String location; // 강의실
    private String startTime;
    private String runningTime;
    private String dayOfWeek; // 요일
}
