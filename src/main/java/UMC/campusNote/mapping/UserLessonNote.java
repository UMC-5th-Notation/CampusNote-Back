package UMC.campusNote.mapping;

import UMC.campusNote.common.BaseEntity;
import UMC.campusNote.note.entity.Note;
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
public class UserLessonNote extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_LESSON_NOTE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_LESSON_ID")
    private UserLesson userLesson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "NOTE_ID")
    private Note note;

    public void setUserLesson(UserLesson userLesson) {
        this.userLesson = userLesson;
        userLesson.getUserLessonNoteList().add(this);
    }

    public void setNote(Note note) {
        this.note = note;
        note.getUserLessonNoteList().add(this);
    }

}