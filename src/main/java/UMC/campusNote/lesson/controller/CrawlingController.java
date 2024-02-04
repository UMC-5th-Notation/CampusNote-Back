package UMC.campusNote.lesson.controller;

import UMC.campusNote.common.ApiResponse;
import UMC.campusNote.common.code.status.ErrorStatus;
import UMC.campusNote.common.code.status.SuccessStatus;
import UMC.campusNote.lesson.dto.CrawlingRequest;
import UMC.campusNote.lesson.dto.LessonDto;
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

    //warn id gap
    @PostMapping(value = "/api/v1/crawl")
    public ApiResponse<List<LessonDto>> crawling(@AuthenticationPrincipal User user,
                                                 @Valid @RequestBody CrawlingRequest crawlingRequest,
                                                 BindingResult bindingResult) {

        log.info("enter CrawlngController : [post] /api/v1/crawl");

        if (bindingResult.hasErrors()) {
            // ex. null, invalid request body data
            throw new CrawlingException(ErrorStatus.CRAWLING_URL_BINDING_FAULT);
        }
        List<LessonDto> results = crawlingService.action(crawlingRequest.getUrl(), user.getId());

        return ApiResponse.of(SuccessStatus.CRAWLING_OK, results);
    }
}
