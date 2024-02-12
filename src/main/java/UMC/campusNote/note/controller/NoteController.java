


package UMC.campusNote.note.controller;

import UMC.campusNote.common.ApiResponse;
import UMC.campusNote.note.dto.NoteRequestDTO;
import UMC.campusNote.note.dto.NoteResponseDTO;
import UMC.campusNote.note.service.NoteService;
import UMC.campusNote.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
    @Operation(summary = "특정 노트 조회 기능",description = "특정 노트를 조회하는 API입니다")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "NOTE202",description = "노트 조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "NOTE4001", description = "존재하지 않는 노트.",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "lessonId", description = "레슨의 아이디, path variable 입니다"),
            @Parameter(name = "noteId", description = "노트의 아이디, path variable 입니다")
    })
    public ApiResponse<NoteResponseDTO.NoteGetDTO> getUserNote(@AuthenticationPrincipal User user, @PathVariable("noteId") Long noteId) {
        return ApiResponse.of(NOTE_GET_ONE, noteService.getUserNote(user, noteId));
    }

    @GetMapping("/{lessonId}")
    @Operation(summary = "특정 유저레슨의 전체 노트 조회 기능",description = "특정 유저레슨의 전체 노트들을 조회하는 API입니다 query String 으로 page 번호를 주세요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "NOTE201",description = "노트 전체 조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "NOTE4001", description = "존재하지 않는 노트.",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "lessonId", description = "레슨의 아이디, path variable 입니다"),
            @Parameter(name = "semester", description = "학기, query string 입니다")
    })
    public ApiResponse<Slice<NoteResponseDTO.NoteGetDTO>> getUserNotes(@AuthenticationPrincipal User user, @PathVariable("lessonId") Long lessonId, @RequestParam("semester") String semester, Pageable pageable) {
        return ApiResponse.of(NOTE_GET_ALL, noteService.getUserNotes(user, lessonId, semester, pageable));
    }

    @PostMapping("/{lessonId}")
    @Operation(summary = "특정 노트 등록하는 API",description = "특정 노트 등록하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "NOTE200",description = "노트 생성 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "LESSON4001", description = "존재하지 않는 수업.",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USER_LESSON4001", description = "회원에게 없는 수업입니다.",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "lessonId", description = "레슨 아이디, path variable 입니다"),
    })
    public ApiResponse<NoteResponseDTO.NoteCreateDTO> createNote(@AuthenticationPrincipal User user, @PathVariable("lessonId") Long lessonId, @RequestBody NoteRequestDTO.NoteCreateDTO request) {
        return ApiResponse.of(NOTE_CREATE, noteService.createUserNote(user, lessonId, request));
    }

    @PatchMapping("/{noteId}")
    @Operation(summary = "특정 노트의 이름 수정 API",description = "특정 노트의 이름을 수정하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "NOTE203",description = "노트 수정 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "NOTE4001", description = "존재하지 않는 수업.",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "noteId", description = "노트의 아이디, path variable 입니다"),
    })
    public ApiResponse<NoteResponseDTO.NoteUpdateDTO> updateNote(@AuthenticationPrincipal User user, @PathVariable("noteId") Long noteId, @RequestBody NoteRequestDTO.NoteUpdateDTO request) {
        return ApiResponse.of(NOTE_UPDATE, noteService.updateUserNote(user, noteId, request));
    }

    @DeleteMapping("/{noteId}")
    @Operation(summary = "특정 노트의 삭제 API",description = "특정 노트를 삭제하는 API입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "NOTE204",description = "노트 삭제 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "NOTE4001", description = "존재하지 않는 수업.",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "noteId", description = "노트의 아이디, path variable 입니다"),
    })
    public ApiResponse<NoteResponseDTO.NoteDeleteDTO> deleteNote(@AuthenticationPrincipal User user, @PathVariable("noteId") Long noteId) {
        return ApiResponse.of(NOTE_DELETE, noteService.deleteUserNote(user, noteId));
    }
}