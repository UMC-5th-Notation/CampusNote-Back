package UMC.campusNote.lesson.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class LessonDto {
    private Long id; // lesson id

    private String university; // 학교
    private String semester; // 학기

    private String lessonName; // 과목명

    @Builder.Default
    private List<LessonDetailsDto> lessonDetailsDtoList = new ArrayList<>();
}
