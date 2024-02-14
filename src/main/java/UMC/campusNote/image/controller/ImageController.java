package UMC.campusNote.image.controller;

import UMC.campusNote.common.ApiResponse;
import UMC.campusNote.image.converter.ImageConverter;
import UMC.campusNote.image.dto.ImageResponseDTO;
import UMC.campusNote.image.entity.Image;
import UMC.campusNote.image.service.ImageService;
import UMC.campusNote.lessonNote.converter.LessonNoteConverter;
import UMC.campusNote.lessonNote.dto.LessonNoteResponseDTO;
import UMC.campusNote.lessonNote.entity.LessonNote;
import UMC.campusNote.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

import static UMC.campusNote.common.code.status.SuccessStatus.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/images")
public class ImageController {
    private final ImageService imageService;

    @PostMapping("/{noteId}")
    @Operation(summary = "이미지 등록 API", description = "특정 노트와 관련된 이미지를 등록하는 API ")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "IMAGE201",description = "이미지 업로드 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "NOTE4001", description = "존재하지 않는 노트.",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
    })
    @Parameters({
            @Parameter(name = "noteId", description = "노트의 아이디, path variable 입니다"),
            @Parameter(name = "lessonNoteFile", description = "강의 노트 파일, Multipart 입니다")
    })
    public ApiResponse<ImageResponseDTO.CreateImageResultDTO> create(@AuthenticationPrincipal User user, @PathVariable("noteId") Long noteId, @RequestParam("imageFile") MultipartFile imageFile) throws IOException {
        Image newImage = imageService.createImage(noteId, imageFile, user.getId());
        return ApiResponse.of(IMAGE_CREATE, ImageConverter.toImageResultDTO(newImage));
    }

    @GetMapping("/{noteId}")
    @Operation(summary = "특정 노트의 모든 이미지를 조회하는 API", description = "특정 노트의 모든 이미지를 조회하는 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "IMAGE200",description = "이미지 조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "NOTE4001", description = "존재하지 않는 노트.",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "IMAGE4001", description = "존재하지 않는 이미지.",content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @Parameters({
            @Parameter(name = "noteId", description = "노트의 아이디, path variable 입니다")
    })
    public ApiResponse<List<ImageResponseDTO.GetImageResultDTO>> getImages(@PathVariable Long noteId) throws IOException{
        return ApiResponse.of(IMAGE_GET_ALL, imageService.getImageOfNote(noteId));
    }


    @GetMapping("/{noteId}/{imageId}")
    @Operation(summary = "특정 노트의 특정 이미지를 조회하는 API", description = "특정 노트의 특정 이미지를 조회하는 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "IMAGE200",description = "이미지 조회 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "NOTE4001", description = "존재하지 않는 노트.",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "IMAGE4001", description = "존재하지 않는 이미지.",content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @Parameters({
            @Parameter(name = "noteId", description = "노트의 아이디, path variable 입니다"),
            @Parameter(name = "imageId", description = "이미지의 아이디, path variable 입니다")
    })
    public ApiResponse<ImageResponseDTO.GetImageResultDTO> getImageOne(@PathVariable Long noteId, @PathVariable Long imageId) throws IOException{
        return ApiResponse.of(IMAGE_GET_ONE, imageService.getOneImageOfNote(noteId, imageId));
    }

    @Transactional
    @DeleteMapping("/{noteId}")
    @Operation(summary = "특정 노트의 모든 이미지를 삭제하는 API", description = "특정 노트의 모든 이미지를 삭제하는 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "IMAGE202",description = "노트의 모든 이미지 삭제 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "NOTE4001", description = "존재하지 않는 노트.",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "IMAGE4001", description = "존재하지 않는 이미지.",content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @Parameters({
            @Parameter(name = "noteId", description = "노트의 아이디, path variable 입니다")
    })
    public ApiResponse<List<ImageResponseDTO.GetImageResultDTO>> deleteImages(@PathVariable Long noteId) throws IOException{
        return ApiResponse.of(IMAGE_DELETE_ALL, imageService.deleteImageOfNote(noteId));
    }

    @Transactional
    @DeleteMapping("/{noteId}/{imageId}")
    @Operation(summary = "특정 노트의 특정 이미지를 삭제하는 API", description = "특정 노트의 특정 이미지를 삭제하는 API")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "IMAGE202",description = "노트의 특정 이미지 삭제 성공"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "NOTE4001", description = "존재하지 않는 노트.",content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "IMAGE4001", description = "존재하지 않는 이미지.",content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @Parameters({
            @Parameter(name = "noteId", description = "노트의 아이디, path variable 입니다"),
            @Parameter(name = "imageId", description = "이미지의 아이디, path variable 입니다")
    })
    public ApiResponse<ImageResponseDTO.GetImageResultDTO> deleteOneImage(@PathVariable Long noteId, @PathVariable Long imageId) throws IOException{
        return ApiResponse.of(IMAGE_DELETE_ONE, imageService.deleteOneImageOfNote(noteId, imageId));
    }
}
