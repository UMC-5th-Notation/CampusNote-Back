package UMC.campusNote.lesson.controller;

import UMC.campusNote.common.ApiResponse;
import UMC.campusNote.common.code.status.ErrorStatus;
import UMC.campusNote.common.code.status.SuccessStatus;
import UMC.campusNote.lesson.dto.LessonRequestDTO;
import UMC.campusNote.lesson.dto.LessonResponseDTO;
import UMC.campusNote.lesson.exception.LessonException;
import UMC.campusNote.lesson.service.LessonService;
import UMC.campusNote.user.entity.User;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
public class LessonController {
    private final LessonService lessonService;

    @GetMapping(value = "/api/v1/lessons")
    @Operation(summary = "특정 학기 수업 전체 조회", description = "로그인한 회원의 특정 학기 수업을 전체 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4001", description = "사용자가 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "LESSON4002", description = "해당학기 수업 정보가 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @Parameters({
            @Parameter(name = "attendedSemester", description = "수강중인 학기(ex. 3-2), RequestParam")
    })
    public ApiResponse<List<LessonResponseDTO.FindResultDTO>> findLessons(@AuthenticationPrincipal User user,
                                                                          @RequestParam(name = "attendedSemester") String attendedSemester) {
        log.info("enter LessonController : [get] /api/v1/lessons?attendedSemester={}", attendedSemester);

        List<LessonResponseDTO.FindResultDTO> result = lessonService.findLessons(user.getId(), attendedSemester);

        return ApiResponse.of(SuccessStatus.OK, result);
    }

    @GetMapping(value = "/api/v1/lessons/{lessonId}")
    @Operation(summary = "특정 수업 조회", description = "lessonId로 특정 수업 정보를 조회합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "LESSON4001", description = "존재하지 않는 수업.",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @Parameters({
            @Parameter(name = "lessonId", description = "특정 수업의 id, PathVariable")
    })
    public ApiResponse<LessonResponseDTO.FindResultDTO> findLessonDetails(@PathVariable(name = "lessonId") Long lessonId) {
        log.info("enter LessonController : [get] /api/v1/lessons/{}", lessonId);

        LessonResponseDTO.FindResultDTO result = lessonService.findLessonDetails(lessonId);

        return ApiResponse.of(SuccessStatus.OK, result);
    }

    @PostMapping(value = "/api/v1/lessons/new")
    @Operation(summary = "수업 생성", description = "수업을 생성하고 로그인한 사용자 정보에 등록합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "LESSON201", description = "수업 생성 성공."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4001", description = "사용자가 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "LESSON4004", description = "파라미터 바인딩 실패.",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "LESSON4003", description = "중복된 수업.",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    public ApiResponse<Long> createLesson(@AuthenticationPrincipal User user,
                                          @Valid @RequestBody LessonRequestDTO.CreateDTO createDTO,
                                          BindingResult bindingResult) {
        log.info("enter LessonController : [post] /api/v1/lessons/new");
        if (bindingResult.hasErrors()) {
            throw new LessonException(ErrorStatus.LESSON_REQUEST_CREATE_BINDING_FAULT);
        }

        Long lessonId = lessonService.createLesson(user.getId(), createDTO);

        return ApiResponse.of(SuccessStatus.LESSON_CREATE, lessonId);
    }

    @DeleteMapping(value = "/api/v1/lessons/{lessonId}")
    @Operation(summary = "특정 수업 삭제", description = "로그인한 회원 정보에서 해당 수업을 삭제합니다.")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "COMMON200", description = "성공입니다."),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER4001", description = "사용자가 없습니다.",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "LESSON4001", description = "존재하지 않는 수업.",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "USERLESSON4001", description = "존재하지 않는 유저레슨.",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @Parameters({
            @Parameter(name = "attendedSemester", description = "수강중인 학기(ex. 3-2), RequestParam")
    })
    public ApiResponse<Long> deleteUserLesson(@AuthenticationPrincipal User user,
                                              @PathVariable(name = "lessonId") Long lessonId) {
        log.info("enter LessonController : [delete] /api/v1/lessons/{}", lessonId);

        Long id = lessonService.deleteUserLesson(user.getId(), lessonId);

        return ApiResponse.of(SuccessStatus.OK, id);
    }
}
