package UMC.campusNote.audio.entity;

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
public class Audio extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "AUDIO_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "NOTE_ID")
    private Note note;

    private String audioFile; // 녹음한 오디오 파일

    public void setNote(Note note) {
        this.note = note;
        note.getAudioList().add(this);
    }
}
