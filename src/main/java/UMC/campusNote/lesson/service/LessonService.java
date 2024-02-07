package UMC.campusNote.lesson.service;

import UMC.campusNote.common.code.status.ErrorStatus;
import UMC.campusNote.common.exception.GeneralException;
import UMC.campusNote.lesson.converter.LessonConverter;
import UMC.campusNote.lesson.dto.CustomLessonRequest;
import UMC.campusNote.lesson.dto.LessonDto;
import UMC.campusNote.lesson.entity.Lesson;
import UMC.campusNote.lesson.exception.LessonException;
import UMC.campusNote.lesson.repository.LessonRepository;
import UMC.campusNote.mapping.UserLesson;
import UMC.campusNote.mapping.repository.UserLessonRepository;
import UMC.campusNote.user.entity.User;
import UMC.campusNote.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class LessonService {
    private final LessonRepository lessonRepository;
    private final UserLessonRepository userLessonRepository;
    private final UserRepository userRepository;

    public List<LessonDto> findLessons(Long userId, String semester) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        List<UserLesson> userLessonList = userLessonRepository.findByUserAndAndAttendedSemester(user, semester)
                .orElseThrow(() -> new LessonException(ErrorStatus.LESSONS_NOT_FOUND));
        if (userLessonList.isEmpty()) {
            throw new LessonException(ErrorStatus.LESSONS_NOT_FOUND);
        }

        return LessonConverter.userLessonsToLessonDtos(userLessonList);
    }

    public LessonDto findLessonDetails(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new LessonException(ErrorStatus.LESSON_NOT_FOUND));

        return LessonConverter.oneLessonToLessonDto(lesson);
    }


    public Long createCustomLesson(Long userId, CustomLessonRequest customLessonRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        Lesson lesson = Lesson.createLesson(user.getUniversity(), customLessonRequest.getSemester(), customLessonRequest.getLessonName(),
                customLessonRequest.getProfessorName(), customLessonRequest.getLocation(), customLessonRequest.getStartTime(),
                customLessonRequest.getRunningTime(), customLessonRequest.getDayOfWeek());

        // dup check
        Lesson findLesson = lessonRepository.findUniqueLesson(lesson).orElse(null);

        if (findLesson == null) {
            // new : save lesson and userLesson
            lessonRepository.save(lesson);
            UserLesson userLesson = UserLesson.createUserLesson(user, lesson, lesson.getSemester());
            userLessonRepository.save(userLesson);

            return lesson.getId();
        } else {
            // not new case
            // dup check
            UserLesson foundLesson = userLessonRepository.findByUserAndAndAttendedSemesterAndAndLesson(
                    user, findLesson.getSemester(), findLesson).orElse(null);

            if (foundLesson == null) {
                // not new && do not have : save userLesson
                UserLesson userLesson = UserLesson.createUserLesson(user, findLesson, findLesson.getSemester());
                userLessonRepository.save(userLesson);
            } else {
                // not new && already have
                throw new LessonException(ErrorStatus.LESSONS_ALREADY_HAVE);
            }

            return findLesson.getId();
        }
    }

    public Long deleteUserLesson(Long userId, Long lessonId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new LessonException(ErrorStatus.LESSON_NOT_FOUND));

        UserLesson foundUserLesson = userLessonRepository
                .findByUserAndAndAttendedSemesterAndAndLesson(user, lesson.getSemester(), lesson)
                .orElseThrow(() -> new LessonException(ErrorStatus.USERLESSON_NOT_FOUND));

        user.getUserLessonList().removeIf(userLesson -> userLesson.equals(foundUserLesson));
        userLessonRepository.deleteById(foundUserLesson.getId());

        return lessonId;
    }
}
