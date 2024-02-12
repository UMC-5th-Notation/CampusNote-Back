package UMC.campusNote.audio.controller;


import UMC.campusNote.audio.dto.AudioResponseDTO;
import UMC.campusNote.audio.service.AudioService;
import UMC.campusNote.common.ApiResponse;
import UMC.campusNote.common.s3.dto.S3UploadRequest;
import UMC.campusNote.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static UMC.campusNote.common.code.status.SuccessStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/audios")
public class AudioController {

    private final AudioService audioService;


    @GetMapping("/{noteId}/{audioId}")
    @Operation(summary = "특정 노트의 특정 녹음 파일 조회 기능",description = "특정 노트의 특정 녹음 파일들의 목록을 조회하는 API입니다")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUDIO202",description = "녹음 파일 조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUDIO4001", description = "존재하지 않는 오디오.",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "noteId", description = "노트의 아이디, path variable 입니다"),
            @Parameter(name = "audioId", description = "오디오의 아이디, path variable 입니다")
    })
    public ApiResponse<AudioResponseDTO.AudioDTO> getAudio(@PathVariable("noteId") Long noteId, @PathVariable("audioId") Long audioId) {
        return ApiResponse.of(AUDIO_GET_ONE, audioService.getAudio(audioId));
    }

    @GetMapping("/{noteId}")
    @Operation(summary = "특정 노트의 전체 녹음 파일 조회",description = "특정 노트의 전체 녹음들의 목록을 조회하는 API이며, 페이징을 포함합니다. query String 으로 page 번호를 주세요")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUDIO201",description = "녹음 파일 전체 조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUDIO4001", description = "존재하지 않는 오디오",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "noteId", description = "노트의 아이디, path variable 입니다")
    })

    public ApiResponse<Slice<AudioResponseDTO.AudioDTO>> getAudios(@PathVariable("noteId") Long noteId, Pageable pageable) {
        return ApiResponse.of(AUDIO_GET_ALL, audioService.getAudios(noteId, pageable));
    }

    @PostMapping("/{noteId}")
    @Operation(summary = "특정 노트의 녹음 파일을 등록하는 API",description = "특정 노트의 녹음 파일을 등록하는 API이며, 녹음 파일을 등록합니다. 녹음 파일은 Multipart입니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUDIO200",description = "녹음 생성 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUDIO4003", description = "오디오 파일 업로드 실패.",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "FILE4001", description = "파일 변환 실패",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "S3UPLOAD4001", description = "S3 파일 업로드 실패.",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "noteId", description = "노트의 아이디, path variable 입니다"),
            @Parameter(name = "audioFile", description = "녹음 파일, Multipart 입니다")
    })
    public ApiResponse<AudioResponseDTO.AudioDTO> uploadAudio(@AuthenticationPrincipal User user, @PathVariable("noteId") Long noteId, @RequestParam("audioFile") MultipartFile audioFile) {
        return ApiResponse.of(AUDIO_CREATE, audioService.saveAudio(new S3UploadRequest(user.getId(), "audios"), noteId, audioFile));
    }

    @DeleteMapping("/{audioId}")
    @Operation(summary = "특정 녹음 파일을 삭제하는 API",description = "특정 노트의 녹음 파일을 삭제하는 API입니다")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUDIO203",description = "녹음 파일 삭제 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "AUDIO4001", description = "존재하지 않는 오디오.",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "audioId", description = "audoi의 아이디, path variable 입니다"),
    })
    public ApiResponse<AudioResponseDTO.AudioDTO> deleteAudio(@PathVariable("audioId") Long audioId) {
        return ApiResponse.of(AUDIO_DELETE, audioService.deleteAudio(audioId));
    }
}
