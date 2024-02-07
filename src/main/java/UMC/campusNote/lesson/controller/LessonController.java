package UMC.campusNote.lesson.controller;

import UMC.campusNote.common.ApiResponse;
import UMC.campusNote.common.code.status.SuccessStatus;
import UMC.campusNote.lesson.dto.CustomLessonRequest;
import UMC.campusNote.lesson.dto.LessonDto;
import UMC.campusNote.lesson.service.LessonService;
import UMC.campusNote.user.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
public class LessonController {
    private final LessonService lessonService;

    @GetMapping(value = "/api/v1/lessons")
    public ApiResponse<List<LessonDto>> findLessons(@AuthenticationPrincipal User user,
                                                    @RequestParam(name = "semester") String semester) {
        log.info("enter LessonController : [get] /api/v1/lessons?semester={}", semester);

        List<LessonDto> result = lessonService.findLessons(user.getId(), semester);

        return ApiResponse.of(SuccessStatus.OK, result);
    }

    @GetMapping(value = "/api/v1/lessons/{lessonId}")
    public ApiResponse<LessonDto> findLessonDetails(@PathVariable(name = "lessonId") Long lessonId) {
        log.info("enter LessonController : [get] /api/v1/lessons/{}", lessonId);

        LessonDto result = lessonService.findLessonDetails(lessonId);

        return ApiResponse.of(SuccessStatus.OK, result);
    }

    @PostMapping(value = "/api/v1/lessons/new")
    public ApiResponse<Long> createCustomLesson(@AuthenticationPrincipal User user,
                                                @RequestBody CustomLessonRequest customLessonRequest) {
        log.info("enter LessonController : [post] /api/v1/lessons/new");

        Long lessonId = lessonService.createCustomLesson(user.getId(), customLessonRequest);

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
