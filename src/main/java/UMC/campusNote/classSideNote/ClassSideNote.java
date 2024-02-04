package UMC.campusNote.classSideNote;

import UMC.campusNote.common.BaseEntity;
import UMC.campusNote.mapping.UserLesson;
import jakarta.persistence.*;
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

    @Column(length = 50)
    private String title; // 제목

    private String content; // 내용

    private LocalDateTime deadline; // 마감기한

    private Integer colorCode; // 노트 색깔
}
