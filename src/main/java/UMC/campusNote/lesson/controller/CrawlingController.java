package UMC.campusNote.lesson.controller;

import UMC.campusNote.common.ApiResponse;
import UMC.campusNote.lesson.converter.LessonConverter;
import UMC.campusNote.lesson.dto.CrawlingRequest;
import UMC.campusNote.lesson.dto.CrawlingResponse;
import UMC.campusNote.lesson.service.CrawlingService;
import UMC.campusNote.mapping.UserLesson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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


    @PostMapping(value = "/api/crawling")
    public ApiResponse<List<CrawlingResponse>> crawling(@RequestBody CrawlingRequest crawlingRequest) {
        log.info("enter CrawlingController");

        List<UserLesson> action = crawlingService.action(crawlingRequest.getUrl());

        List<CrawlingResponse> responses = LessonConverter.toCrawlingResponseList(action);

        return ApiResponse.onSuccess(responses);
    }
}
