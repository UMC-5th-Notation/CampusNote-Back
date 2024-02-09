package UMC.campusNote.note.controller;

import UMC.campusNote.common.ApiResponse;
import UMC.campusNote.note.dto.NoteRequestDTO;
import UMC.campusNote.note.dto.NoteResponseDTO;
import UMC.campusNote.note.service.NoteService;
import UMC.campusNote.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static UMC.campusNote.common.code.status.SuccessStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notes")
@Slf4j
public class NoteController {

    private final NoteService noteService;

    @GetMapping("/{lessonId}/{noteId}")
    public ApiResponse<NoteResponseDTO.NoteGetDTO> getUserNote(@AuthenticationPrincipal User user, @PathVariable("noteId") Long noteId) {
        return ApiResponse.of(NOTE_GET_ONE, noteService.getUserNote(user, noteId));
    }

    @GetMapping("/{lessonId}")
    public ApiResponse<Slice<NoteResponseDTO.NoteGetDTO>> getUserNotes(@AuthenticationPrincipal User user, @PathVariable("lessonId") Long lessonId, @RequestParam("semester") String semester, Pageable pageable) {
        return ApiResponse.of(NOTE_GET_ALL, noteService.getUserNotes(user, lessonId, semester, pageable));
    }

    @PostMapping("/{lessonId}")
    public ApiResponse<NoteResponseDTO.NoteCreateDTO> createNote(@AuthenticationPrincipal User user, @PathVariable("lessonId") Long lessonId, @RequestBody NoteRequestDTO.NoteCreateDTO request) {
        return ApiResponse.of(NOTE_CREATE, noteService.createUserNote(user, lessonId, request));
    }
}
