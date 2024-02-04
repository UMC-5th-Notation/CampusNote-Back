package UMC.campusNote.lesson.converter;


import UMC.campusNote.lesson.dto.LessonDto;
import UMC.campusNote.lesson.dto.LessonDetailsDto;
import UMC.campusNote.lesson.entity.Lesson;
import UMC.campusNote.mapping.UserLesson;

import java.util.ArrayList;
import java.util.List;

public class LessonConverter {
    public static List<LessonDto> userLessonsToLessonDtos(List<UserLesson> action) {

        List<LessonDto> responses = new ArrayList<>();

        for (UserLesson memberLesson : action) {
//            Member member = memberLesson.getMember();
            Lesson lesson = memberLesson.getLesson();

            boolean dup = false;
            LessonDto dupResponse = null;
            if (!responses.isEmpty()) {
                for (LessonDto lessonDto : responses) {
                    if (lessonDto.getLessonName().equals(lesson.getLessonName())) {
                        dup = true;
                        dupResponse = lessonDto;
                    }
                }
            }

            LessonDetailsDto lessonDetailsDto = LessonDetailsDto.builder()
                    .professorName(lesson.getProfessorName())
                    .location(lesson.getLocation())
                    .startTime(lesson.getStartTime())
                    .runningTime(lesson.getRunningTime())
                    .dayOfWeek(lesson.getDayOfWeek())
                    .build();

            if (dup) {
                dupResponse.getLessonDetailsDtoList().add(lessonDetailsDto);
            } else {
                LessonDto lessonDto = LessonDto.builder()
                        .id(lesson.getId())
                        .university(lesson.getUniversity())
                        .semester(lesson.getSemester())
                        .lessonName(lesson.getLessonName())
                        .build();
                lessonDto.getLessonDetailsDtoList().add(lessonDetailsDto);

                responses.add(lessonDto);
            }
        }
        return responses;
    }

    public static LessonDto oneLessonToLessonDto(Lesson lesson) {

        LessonDetailsDto build = LessonDetailsDto.builder()
                .professorName(lesson.getProfessorName())
                .location(lesson.getLocation())
                .startTime(lesson.getStartTime())
                .runningTime(lesson.getRunningTime())
                .dayOfWeek(lesson.getDayOfWeek())
                .build();
        List<LessonDetailsDto> list = new ArrayList<>();
        list.add(build);
        return LessonDto.builder()
                .id(lesson.getId())
                .university(lesson.getUniversity())
                .semester(lesson.getSemester())
                .lessonName(lesson.getLessonName())
                .lessonDetailsDtoList(list)
                .build();
    }

}
