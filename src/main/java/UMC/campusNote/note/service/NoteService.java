package UMC.campusNote.note.service;

import UMC.campusNote.note.dto.NoteRequestDTO;
import UMC.campusNote.note.dto.NoteResponseDTO;
import UMC.campusNote.user.entity.User;

public interface NoteService {

    NoteResponseDTO.NoteCreateDTO createUserNote(User user, NoteRequestDTO.NoteCreateDTO request);
}
