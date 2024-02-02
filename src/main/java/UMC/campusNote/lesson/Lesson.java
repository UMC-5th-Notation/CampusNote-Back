package UMC.campusNote.lesson;

import UMC.campusNote.common.BaseEntity;
import UMC.campusNote.lessonDetail.LessonDetail;
import UMC.campusNote.mapping.UserLesson;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Lesson extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LESSON_ID")
    private Long id;

    @Column(length = 20)
    private String university; // 학교

    @Column(length = 50)
    private String lessonName; // 수업명

    @Column(length = 15)
    private String semester; // 학기


    @OneToMany(mappedBy = "lesson")
    @Builder.Default // builder 패턴 사용시 null pointer exception 발생 방지
    List<UserLesson> userLessonList = new ArrayList<>();

    @OneToMany(mappedBy = "lesson")
    @Builder.Default
    List<LessonDetail> lessonDetailList = new ArrayList<>();
}
