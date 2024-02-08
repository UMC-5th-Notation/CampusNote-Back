package UMC.campusNote.note.converter;

import UMC.campusNote.note.dto.NoteResponseDTO;
import UMC.campusNote.note.entity.Note;
import UMC.campusNote.page.dto.PageRequestDTO;
import UMC.campusNote.page.dto.PageResponseDTO;
import UMC.campusNote.page.entity.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NoteConverter {

    public static NoteResponseDTO.NoteCreateDTO toNoteCreateDTO(Note note){
        return NoteResponseDTO.NoteCreateDTO.builder()
                .noteId(note.getId())
                .build();
    }
}
