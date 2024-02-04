package UMC.campusNote.note;

import UMC.campusNote.audio.Audio;
import UMC.campusNote.common.BaseEntity;
import UMC.campusNote.image.Image;
import UMC.campusNote.lessonNote.LessonNote;
import UMC.campusNote.mapping.UserLessonNote;
import UMC.campusNote.page.Page;
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
public class Note extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTE_ID")
    private Long id;

    @Column(length = 50)
    private String noteName;

    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL)
    @Builder.Default
    private List<UserLessonNote> userLessonNoteList = new ArrayList<>();

    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Page> pageList  = new ArrayList<>();

    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Image> imageList = new ArrayList<>();

    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL)
    @Builder.Default
    private List<LessonNote> lessonNoteList = new ArrayList<>();

    @OneToMany(mappedBy = "note", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Audio> audioList = new ArrayList<>();
}
