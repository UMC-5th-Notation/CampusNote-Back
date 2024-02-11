package UMC.campusNote.lesson.service;

import UMC.campusNote.common.code.status.ErrorStatus;
import UMC.campusNote.common.exception.GeneralException;
import UMC.campusNote.lesson.converter.LessonConverter;
import UMC.campusNote.lesson.dto.LessonRequestDTO;
import UMC.campusNote.lesson.dto.LessonResponseDTO;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class LessonService {
    private final LessonRepository lessonRepository;
    private final UserLessonRepository userLessonRepository;
    private final UserRepository userRepository;

    public List<LessonResponseDTO.FindResultDTO> findLessons(Long userId, String attendedSemester) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        List<UserLesson> userLessonList = userLessonRepository.findByUserAndAttendedSemester(user, attendedSemester)
                .orElseThrow(() -> new LessonException(ErrorStatus.LESSONS_NOT_FOUND));
        if (userLessonList.isEmpty()) {
            throw new LessonException(ErrorStatus.LESSONS_NOT_FOUND);
        }

        return LessonConverter.toCreateResultDTOList(userLessonList);
    }

    public LessonResponseDTO.FindResultDTO findLessonDetails(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new LessonException(ErrorStatus.LESSON_NOT_FOUND));

        return LessonConverter.toCreateResultDTO(lesson);
    }

    @Transactional
    public Long createLesson(Long userId, LessonRequestDTO.CreateDTO createDTO) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        String university = user.getUniversity();
        Lesson lesson = Lesson.createLesson(university, createDTO.getSemester(),
                createDTO.getLessonName(), createDTO.getProfessorName(), createDTO.getLocation(),
                createDTO.getStartTime(), createDTO.getRunningTime(), createDTO.getDayOfWeek());

        // dup check
        Lesson findLesson = lessonRepository.findUniqueLesson(lesson).orElse(null);

        if (findLesson == null) {
            // new : save lesson and userLesson
            lessonRepository.save(lesson);
            UserLesson userLesson = UserLesson.createUserLesson(user, lesson, createDTO.getAttendedSemester());
            userLessonRepository.save(userLesson);

            return lesson.getId();
        } else {
            // not new case
            // dup check
            UserLesson findUserLesson = userLessonRepository.findByUserAndAttendedSemesterAndLesson(
                    user, createDTO.getAttendedSemester(), findLesson).orElse(null);

            if (findUserLesson == null) {
                // not new && do not have : save userLesson
                UserLesson userLesson = UserLesson.createUserLesson(user, findLesson, createDTO.getAttendedSemester());
                userLessonRepository.save(userLesson);
            } else {
                // not new && already have
                throw new LessonException(ErrorStatus.LESSONS_ALREADY_HAVE);
            }

            return findLesson.getId();
        }
    }

    @Transactional
    public Long deleteUserLesson(Long userId, Long lessonId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));

        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new LessonException(ErrorStatus.LESSON_NOT_FOUND));

        UserLesson foundUserLesson = userLessonRepository
                .findByUserAndLesson(user, lesson)
                .orElseThrow(() -> new LessonException(ErrorStatus.USERLESSON_NOT_FOUND));

        user.getUserLessonList().removeIf(userLesson -> userLesson.equals(foundUserLesson));
        userLessonRepository.deleteById(foundUserLesson.getId());

        return lessonId;
    }
}
