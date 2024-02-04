package UMC.campusNote.page;

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
public class Page extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PAGE_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "NOTE_ID")
    private Note note;

    private String handWritingSVG; // 필기 svg

    private Integer pageNumber; // 페이지 넘버

    private String sideNote;

    private Integer round; // 회차
}