package UMC.campusNote.lessonNote.controller;

import UMC.campusNote.common.ApiResponse;
import UMC.campusNote.lessonNote.converter.LessonNoteConverter;
import UMC.campusNote.lessonNote.dto.LessonNoteResponseDTO;
import UMC.campusNote.lessonNote.entity.LessonNote;
import UMC.campusNote.lessonNote.service.LessonNoteService;
import UMC.campusNote.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

import static UMC.campusNote.common.code.status.SuccessStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/lessonNotes")
public class LessonNoteController {
    private final LessonNoteService lessonNoteService;
    @PostMapping("/{noteId}")
    @Operation(summary = "강의 노트 등록 API", description = "특정 노트의 강의 노트를 등록하는 API ")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "LESSONNOTE201",description = "강의 노트 업로드 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "NOTE4001", description = "존재하지 않는 노트.",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "noteId", description = "노트의 아이디, path variable 입니다"),
            @Parameter(name = "lessonNoteFile", description = "강의 노트 파일, Multipart 입니다")
    })
    public ApiResponse<LessonNoteResponseDTO.CreateResultDTO> create(@AuthenticationPrincipal User user, @PathVariable("noteId") Long noteId, @RequestParam("lessonNoteFile") MultipartFile lessonNoteFile) throws IOException {
        LessonNote newLessonNote = lessonNoteService.createLessonNote(noteId, lessonNoteFile, user.getId());
        return ApiResponse.of(LESSONNOTE_CREATE, LessonNoteConverter.toLessonNoteResultDTO(newLessonNote));
    }

    @GetMapping("/{noteId}")
    @Operation(summary = "특정 노트의 강의 노트를 조회하는 API", description = "특정 노트의 강의 노트를 조회하는 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "LESSONNOTE200",description = "강의 노트 조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "NOTE4001", description = "존재하지 않는 노트.",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "LESSONNOTE4001", description = "존재하지 않는 강의 노트.",content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @Parameters({
            @Parameter(name = "noteId", description = "노트의 아이디, path variable 입니다")
    })
    public ApiResponse<LessonNoteResponseDTO.CreateResultDTO> getLessonNotes(@PathVariable Long noteId) throws IOException {
        LessonNote lessonNote = lessonNoteService.getLessonNoteByNoteId(noteId);
        return ApiResponse.of(LESSONNOTE_GET, LessonNoteConverter.toLessonNoteResultDTO(lessonNote));
    }

    @DeleteMapping("/{noteId}")
    @Operation(summary = "특정 노트의 강의 노트를 삭제하는 API", description = "특정 노트의 강의 노트를 삭제하는 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "LESSONNOTE200",description = "강의 노트 조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "NOTE4001", description = "존재하지 않는 노트.",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "LESSONNOTE4001", description = "존재하지 않는 강의 노트.",content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @Parameters({
            @Parameter(name = "noteId", description = "노트의 아이디, path variable 입니다")
    })
    public ApiResponse<LessonNoteResponseDTO.CreateResultDTO> deleteLessonNotes(@PathVariable Long noteId) throws IOException {
        LessonNote lessonNote = lessonNoteService.deleteLessonNoteByNoteId(noteId);
        return ApiResponse.of(LESSONNOTE_DELETE, LessonNoteConverter.toLessonNoteResultDTO(lessonNote));
    }





}
