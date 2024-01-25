package UMC.campusNote.mapping;

import UMC.campusNote.classSideNote.ClassSideNote;
import UMC.campusNote.common.BaseEntity;
import UMC.campusNote.lesson.entity.Lesson;
import UMC.campusNote.user.User;
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
public class UserLesson extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_LESSON_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "LESSON_ID")
    private Lesson lesson;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(length = 10)
    private String attendedSemester; // 수강 학기

    @OneToMany(mappedBy = "userLesson", cascade = CascadeType.ALL)
    @Builder.Default
    private List<ClassSideNote> classSideNoteList = new ArrayList<>();

    @OneToMany(mappedBy = "userLesson", cascade = CascadeType.ALL)
    @Builder.Default
    private List<UserLessonNote> userLessonNoteList = new ArrayList<>();

    public void setUserAndLesson(User user, Lesson lesson){
        // 예외 처리 필요
        this.user = user;
        this.lesson = lesson;

        user.getUserLessonList().add(this);
        lesson.getUserLessonList().add(this);
    }
}
