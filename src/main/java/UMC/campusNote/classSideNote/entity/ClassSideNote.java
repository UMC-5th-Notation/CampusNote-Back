package UMC.campusNote.classSideNote.entity;

import UMC.campusNote.classSideNote.dto.ClassSideNoteRequest;
import UMC.campusNote.common.BaseEntity;
import UMC.campusNote.mapping.UserLesson;
import UMC.campusNote.user.User;
import jakarta.persistence.*;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassSideNote extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CLASS_SIDE_NOTE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_LESSON_ID")
    private UserLesson userLesson;

    @Column(length = 5000, nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean isTodo;

    @Column(nullable = false)
    private Integer colorCode;

    @Column(nullable = true)
    private LocalDate deadline;
    // 할일인 경우에는 deadline 을 받아오고
    // 그냥 사이드 노트인 경우에는 deadline이 null 일 수 있다는 생각입니다.

    public ClassSideNote update(ClassSideNoteRequest form) {
        content = form.getContent();
        isTodo = form.getIsTodo();
        colorCode = form.getColorCode();
        deadline = form.getDeadline();
        return this;
    }
}
