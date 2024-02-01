package UMC.campusNote.lesson.entity;

import UMC.campusNote.common.BaseEntity;
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

    private String professorName; // 교수명
    private String location; // 강의실
    private String startTime;
    private String runningTime;
    private String dayOfWeek; // 요일

    @OneToMany(mappedBy = "lesson")
    @Builder.Default // builder 패턴 사용시 null pointer exception 발생 방지
    List<UserLesson> userLessonList = new ArrayList<>();

//    @OneToMany(mappedBy = "lesson")
//    @Builder.Default
//    List<LessonDetail> lessonDetailList = new ArrayList<>();

    public static Lesson createLesson(String university, String semester, String lessonName, String professorName,
                                      String location, String startTime, String runningTime, String dayOfWeek) {
        return Lesson.builder()
                .university(university)
                .semester(semester)
                .lessonName(lessonName)
                .professorName(professorName)
                .location(location)
                .startTime(startTime)
                .runningTime(runningTime)
                .dayOfWeek(dayOfWeek)
                .build();
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", university='" + university + '\'' +
                ", lessonName='" + lessonName + '\'' +
                ", semester='" + semester + '\'' +
                ", professorName='" + professorName + '\'' +
                ", location='" + location + '\'' +
                ", startTime='" + startTime + '\'' +
                ", runningTime='" + runningTime + '\'' +
                ", dayOfWeek='" + dayOfWeek + '\'' +
                ", userLessonList=" + userLessonList +
                '}';
    }
}
