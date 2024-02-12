package UMC.campusNote.lesson.converter;

import UMC.campusNote.lesson.dto.LessonResponseDTO;
import UMC.campusNote.lesson.entity.Lesson;
import UMC.campusNote.mapping.UserLesson;

import java.util.ArrayList;
import java.util.List;

public class LessonConverter {
    public static List<LessonResponseDTO.FindResultDTO> toCreateResultDTOList(List<UserLesson> action) {

        List<LessonResponseDTO.FindResultDTO> responses = new ArrayList<>();

        for (UserLesson memberLesson : action) {
            Lesson lesson = memberLesson.getLesson();

            boolean dup = false;
            LessonResponseDTO.FindResultDTO dupResponse = null;
            if (!responses.isEmpty()) {
                for (LessonResponseDTO.FindResultDTO findResultDTO : responses) {
                    if (findResultDTO.getLessonName().equals(lesson.getLessonName())) {
                        dup = true;
                        dupResponse = findResultDTO;
                    }
                }
            }

            LessonResponseDTO.FindResultDetailsDTO findResultDetailsDTO = LessonResponseDTO.FindResultDetailsDTO.builder()
                    .professorName(lesson.getProfessorName())
                    .location(lesson.getLocation())
                    .startTime(lesson.getStartTime())
                    .runningTime(lesson.getRunningTime())
                    .dayOfWeek(lesson.getDayOfWeek())
                    .build();

            if (dup) {
                dupResponse.getFindResultDetailsDTOList().add(findResultDetailsDTO);
            } else {
                LessonResponseDTO.FindResultDTO findResultDTO = LessonResponseDTO.FindResultDTO.builder()
                        .id(lesson.getId())
                        .university(lesson.getUniversity())
                        .semester(lesson.getSemester())
                        .lessonName(lesson.getLessonName())
                        .build();
                findResultDTO.getFindResultDetailsDTOList().add(findResultDetailsDTO);

                responses.add(findResultDTO);
            }
        }
        return responses;
    }

    public static LessonResponseDTO.FindResultDTO toCreateResultDTO(Lesson lesson) {

        LessonResponseDTO.FindResultDetailsDTO build = LessonResponseDTO.FindResultDetailsDTO.builder()
                .professorName(lesson.getProfessorName())
                .location(lesson.getLocation())
                .startTime(lesson.getStartTime())
                .runningTime(lesson.getRunningTime())
                .dayOfWeek(lesson.getDayOfWeek())
                .build();
        List<LessonResponseDTO.FindResultDetailsDTO> list = new ArrayList<>();
        list.add(build);
        return LessonResponseDTO.FindResultDTO.builder()
                .id(lesson.getId())
                .university(lesson.getUniversity())
                .semester(lesson.getSemester())
                .lessonName(lesson.getLessonName())
                .findResultDetailsDTOList(list)
                .build();
    }

}
