package UMC.campusNote.lesson.controller;

import UMC.campusNote.common.ApiResponse;
import UMC.campusNote.common.code.status.ErrorStatus;
import UMC.campusNote.common.code.status.SuccessStatus;
import UMC.campusNote.lesson.dto.LessonRequestDTO;
import UMC.campusNote.lesson.dto.LessonResponseDTO;
import UMC.campusNote.lesson.exception.CrawlingException;
import UMC.campusNote.lesson.service.CrawlingService;
import UMC.campusNote.user.entity.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
public class CrawlingController {

    private final CrawlingService crawlingService;

    //test url
    //https://everytime.kr/@zjmhATXF5czcDHm78zDZ
    //https://everytime.kr/@MVXWO0FP3qdoA0tGis4c

    @PostMapping(value = "/api/v1/crawl")
    public ApiResponse<List<LessonResponseDTO.FindResultDTO>> crawling(@AuthenticationPrincipal User user,
                                                                       @Valid @RequestBody LessonRequestDTO.CrawlingDTO crawlingDTO,
                                                                       BindingResult bindingResult) {

        log.info("enter CrawlngController : [post] /api/v1/crawl");

        if (bindingResult.hasErrors()) {
            throw new CrawlingException(ErrorStatus.CRAWLING_URL_BINDING_FAULT);
        }
        List<LessonResponseDTO.FindResultDTO> results = crawlingService.action(crawlingDTO.getUrl(), user.getId());

        return ApiResponse.of(SuccessStatus.CRAWLING_OK, results);
    }
}
