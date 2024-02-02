package UMC.campusNote.image;

import UMC.campusNote.common.BaseEntity;
import UMC.campusNote.note.Note;
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
public class Image extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IMAGE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "NOTE_ID")
    private Note note;

    private String img;
}
