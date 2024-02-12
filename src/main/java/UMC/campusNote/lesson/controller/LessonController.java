package UMC.campusNote.lesson.controller;

import UMC.campusNote.common.ApiResponse;
import UMC.campusNote.common.code.status.ErrorStatus;
import UMC.campusNote.common.code.status.SuccessStatus;
import UMC.campusNote.lesson.dto.LessonRequestDTO;
import UMC.campusNote.lesson.dto.LessonResponseDTO;
import UMC.campusNote.lesson.exception.LessonException;
import UMC.campusNote.lesson.service.LessonService;
import UMC.campusNote.user.entity.User;
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
    public ApiResponse<List<LessonResponseDTO.FindResultDTO>> findLessons(@AuthenticationPrincipal User user,
                                                                          @RequestParam(name = "attendedSemester") String attendedSemester) {
        log.info("enter LessonController : [get] /api/v1/lessons?attendedSemester={}", attendedSemester);

        List<LessonResponseDTO.FindResultDTO> result = lessonService.findLessons(user.getId(), attendedSemester);

        return ApiResponse.of(SuccessStatus.OK, result);
    }

    @GetMapping(value = "/api/v1/lessons/{lessonId}")
    public ApiResponse<LessonResponseDTO.FindResultDTO> findLessonDetails(@PathVariable(name = "lessonId") Long lessonId) {
        log.info("enter LessonController : [get] /api/v1/lessons/{}", lessonId);

        LessonResponseDTO.FindResultDTO result = lessonService.findLessonDetails(lessonId);

        return ApiResponse.of(SuccessStatus.OK, result);
    }

    @PostMapping(value = "/api/v1/lessons/new")
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
    public ApiResponse<Long> deleteUserLesson(@AuthenticationPrincipal User user,
                                              @PathVariable(name = "lessonId") Long lessonId) {
        log.info("enter LessonController : [delete] /api/v1/lessons/{}", lessonId);

        Long id = lessonService.deleteUserLesson(user.getId(), lessonId);

        return ApiResponse.of(SuccessStatus.OK, id);
    }
}
