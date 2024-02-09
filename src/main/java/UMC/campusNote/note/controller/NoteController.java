package UMC.campusNote.note.controller;

import UMC.campusNote.common.ApiResponse;
import UMC.campusNote.note.dto.NoteRequestDTO;
import UMC.campusNote.note.dto.NoteResponseDTO;
import UMC.campusNote.note.service.NoteService;
import UMC.campusNote.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static UMC.campusNote.common.code.status.SuccessStatus.NOTE_CREATE;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notes")
@Slf4j
public class NoteController {

    private final NoteService noteService;

    @PostMapping
    public ApiResponse<NoteResponseDTO.NoteCreateDTO> createNote(@AuthenticationPrincipal User user, @RequestBody NoteRequestDTO.NoteCreateDTO request) {
        return ApiResponse.of(NOTE_CREATE, noteService.createUserNote(user, request));
    }
}
