package UMC.campusNote.lesson.converter;


import UMC.campusNote.lesson.dto.CrawlingResponse;
import UMC.campusNote.lesson.dto.CrawlingResponseDetails;
import UMC.campusNote.lesson.entity.Lesson;
import UMC.campusNote.mapping.UserLesson;

import java.util.ArrayList;
import java.util.List;

public class LessonConverter {
    public static List<CrawlingResponse> toCrawlingResponseList(List<UserLesson> action) {

        List<CrawlingResponse> responses = new ArrayList<>();

        for (UserLesson memberLesson : action) {
//            Member member = memberLesson.getMember();
            Lesson lesson = memberLesson.getLesson();

            boolean dup = false;
            CrawlingResponse dupResponse = null;
            if (!responses.isEmpty()) {
                for (CrawlingResponse crawlingResponse : responses) {
                    if (crawlingResponse.getLessonName().equals(lesson.getLessonName())) {
                        dup = true;
                        dupResponse = crawlingResponse;
                    }
                }
            }

            CrawlingResponseDetails crawlingResponseDetails = CrawlingResponseDetails.builder()
                    .professorName(lesson.getProfessorName())
                    .location(lesson.getLocation())
                    .startTime(lesson.getStartTime())
                    .runningTime(lesson.getRunningTime())
                    .dayOfWeek(lesson.getDayOfWeek())
                    .build();

            if (dup) {
                dupResponse.getCrawlingResponseDetailsList().add(crawlingResponseDetails);
            } else {
                CrawlingResponse crawlingResponse = CrawlingResponse.builder()
                        .id(lesson.getId())
                        .university(lesson.getUniversity())
                        .semester(lesson.getSemester())
                        .lessonName(lesson.getLessonName())
                        .build();
                crawlingResponse.getCrawlingResponseDetailsList().add(crawlingResponseDetails);

                responses.add(crawlingResponse);
            }
        }
        return responses;
    }

}
