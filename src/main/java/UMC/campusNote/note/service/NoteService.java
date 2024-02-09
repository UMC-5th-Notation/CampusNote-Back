package UMC.campusNote.note.service;

import UMC.campusNote.note.dto.NoteRequestDTO;
import UMC.campusNote.note.dto.NoteResponseDTO;
import UMC.campusNote.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface NoteService {

    Slice<NoteResponseDTO.NoteGetDTO> getUserNotes(User user, Long lessonId, String semester, Pageable pageable);
    NoteResponseDTO.NoteCreateDTO createUserNote(User user, Long lessonId, NoteRequestDTO.NoteCreateDTO request);
}
