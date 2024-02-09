package UMC.campusNote.note.service;

import UMC.campusNote.note.dto.NoteRequestDTO;
import UMC.campusNote.note.dto.NoteResponseDTO;
import UMC.campusNote.user.entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface NoteService {

    NoteResponseDTO.NoteGetDTO getUserNote(User user, Long noteId);
    Slice<NoteResponseDTO.NoteGetDTO> getUserNotes(User user, Long lessonId, String semester, Pageable pageable);
    NoteResponseDTO.NoteCreateDTO createUserNote(User user, Long lessonId, NoteRequestDTO.NoteCreateDTO request);
    NoteResponseDTO.NoteUpdateDTO updateUserNote(User user, Long noteId, NoteRequestDTO.NoteUpdateDTO request);
    NoteResponseDTO.NoteDeleteDTO deleteUserNote(User user, Long noteId);
}
