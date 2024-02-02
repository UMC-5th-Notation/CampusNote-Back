package UMC.campusNote.lessonDetail;

import UMC.campusNote.common.BaseEntity;
import UMC.campusNote.lesson.Lesson;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LessonDetail extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LESSON_DETAIL_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "LESSON_ID")
    private Lesson lesson;

    @Column(length = 30)
    private String location; // 강의실

    @Column(length = 100)
    private String runningTime; // 수업 시간

    @Column(length = 100)
    private String startTime; // 시작 시간

    private Integer dayOfWeek; // 요일

    @Column(length = 20)
    private String professorName; // 교수명
}
