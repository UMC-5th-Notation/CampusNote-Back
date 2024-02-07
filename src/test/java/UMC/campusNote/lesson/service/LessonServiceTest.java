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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LessonServiceTest {
    @InjectMocks
    LessonService lessonService;
    @Mock
    UserRepository userRepository;
    @Mock
    UserLessonRepository userLessonRepository;
    @Mock
    LessonRepository lessonRepository;


    @Test
    @DisplayName("[특정학기 수업 조회 실패] invalid memberId")
    void findLessons_fail_invalid_memberId() {

        User user = new User();
        userRepository.save(user);

        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.empty());

        GeneralException exception = assertThrows(
                GeneralException.class, () -> {
                    lessonService.findLessons(user.getId(), "semester");
                });

        assertThat(exception.getErrorReasonHttpStatus())
                .isEqualTo(ErrorStatus.USER_NOT_FOUND.getReasonHttpStatus());
    }

    @Test
    @DisplayName("[특정학기 수업 조회 실패] empty list")
    void findLessons_fail_empty_list() {

        User user = new User();
        userRepository.save(user);

        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));
        when(userLessonRepository.findByUserAndAndAttendedSemester(user, "semester"))
                .thenReturn(Optional.empty());

        LessonException exception = assertThrows(
                LessonException.class, () -> {
                    lessonService.findLessons(user.getId(), "semester");
                });

        assertThat(exception.getErrorReasonHttpStatus())
                .isEqualTo(ErrorStatus.LESSONS_NOT_FOUND.getReasonHttpStatus());
    }

    @Test
    @DisplayName("[특정학기 수업 조회 성공]")
    void findLessons_success() {

        User user = new User();
        userRepository.save(user);
        Lesson lesson = new Lesson();
        lessonRepository.save(lesson);
        UserLesson userLesson = UserLesson.createUserLesson(user, lesson, "semester");
        userLessonRepository.save(userLesson);

        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));
        when(userLessonRepository.findByUserAndAndAttendedSemester(user, "semester"))
                .thenReturn(Optional.of(user.getUserLessonList()));

        List<LessonDto> lessons = lessonService.findLessons(user.getId(), "semester");

        assertThat(lessons).isEqualTo(LessonConverter.userLessonsToLessonDtos(user.getUserLessonList()));
    }

    @Test
    @DisplayName("[특정수업 조회 실패] invalid lessonId")
    void findLessonDetails_fail_invalid_lessonId() {

        Lesson lesson = new Lesson();
        lessonRepository.save(lesson);

        when(lessonRepository.findById(lesson.getId()))
                .thenReturn(Optional.empty());

        LessonException exception = assertThrows(
                LessonException.class, () -> {
                    lessonService.findLessonDetails(lesson.getId());
                });

        assertThat(exception.getErrorReasonHttpStatus())
                .isEqualTo(ErrorStatus.LESSON_NOT_FOUND.getReasonHttpStatus());
    }

    @Test
    @DisplayName("[특정수업 조회 성공]")
    void findLessonDetails_success() {

        Lesson lesson = new Lesson();
        lessonRepository.save(lesson);

        when(lessonRepository.findById(lesson.getId()))
                .thenReturn(Optional.of(lesson));

        assertThat(lessonService.findLessonDetails(lesson.getId()))
                .isEqualTo(LessonConverter.oneLessonToLessonDto(lesson));
    }

    @Test
    @DisplayName("[커스텀 수업 생성 실패] invalid userId")
    void createCustomLesson_fail_invalid_userId() {

        CustomLessonRequest customLessonRequest = new CustomLessonRequest();
        User user = new User();
        userRepository.save(user);

        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.empty());

        GeneralException exception = assertThrows(
                GeneralException.class, () -> {
                    lessonService.createCustomLesson(user.getId(), customLessonRequest);
                });

        assertThat(exception.getErrorReasonHttpStatus())
                .isEqualTo(ErrorStatus.USER_NOT_FOUND.getReasonHttpStatus());
    }

    @Test
    @DisplayName("[커스텀 수업 생성 성공] new(o)")
    void createCustomLesson_success_new() {
        CustomLessonRequest request = new CustomLessonRequest("Semester", "LessonName",
                "ProfessorName", "Location", "StartTime", "RunningTime", "DayOfWeek");
        User user = new User();
        userRepository.save(user);

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(lessonRepository.findUniqueLesson(any(Lesson.class))).thenReturn(Optional.empty());

        lessonService.createCustomLesson(user.getId(), request);

        verify(lessonRepository, times(1)).save(any(Lesson.class));
        verify(userLessonRepository, times(1)).save(any(UserLesson.class));
    }

    @Test
    @DisplayName("[커스텀 수업 생성 성공] new(x) dup(x)")
    void createCustomLesson_success_notNew_notDup() {
        CustomLessonRequest customLessonRequest = new CustomLessonRequest("Semester", "LessonName",
                "ProfessorName", "Location", "StartTime", "RunningTime", "DayOfWeek");
        User user = new User();
        userRepository.save(user);

        Lesson existingLesson = Lesson.createLesson(user.getUniversity(), customLessonRequest.getSemester(), customLessonRequest.getLessonName(),
                customLessonRequest.getProfessorName(), customLessonRequest.getLocation(), customLessonRequest.getStartTime(),
                customLessonRequest.getRunningTime(), customLessonRequest.getDayOfWeek());

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(lessonRepository.findUniqueLesson(any(Lesson.class))).thenReturn(Optional.of(existingLesson));
        when(userLessonRepository.findByUserAndAndAttendedSemesterAndAndLesson(user, existingLesson.getSemester(), existingLesson))
                .thenReturn(Optional.empty());

        lessonService.createCustomLesson(user.getId(), customLessonRequest);

        verify(lessonRepository, times(0)).save(any(Lesson.class));
        verify(userLessonRepository, times(1)).save(any(UserLesson.class));
    }

    @Test
    @DisplayName("[커스텀 수업 생성 실패] new(x) dup(o)")
    void createCustomLesson_success_notNew_Dup() {
        CustomLessonRequest customLessonRequest = new CustomLessonRequest("Semester", "LessonName",
                "ProfessorName", "Location", "StartTime", "RunningTime", "DayOfWeek");
        User user = new User();
        userRepository.save(user);

        UserLesson userLesson = new UserLesson();

        Lesson existingLesson = Lesson.createLesson(user.getUniversity(), customLessonRequest.getSemester(), customLessonRequest.getLessonName(),
                customLessonRequest.getProfessorName(), customLessonRequest.getLocation(), customLessonRequest.getStartTime(),
                customLessonRequest.getRunningTime(), customLessonRequest.getDayOfWeek());

        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(lessonRepository.findUniqueLesson(any(Lesson.class))).thenReturn(Optional.of(existingLesson));
        when(userLessonRepository.findByUserAndAndAttendedSemesterAndAndLesson(user, existingLesson.getSemester(), existingLesson))
                .thenReturn(Optional.of(userLesson));


        LessonException exception = assertThrows(
                LessonException.class, () -> {
                    lessonService.createCustomLesson(user.getId(), customLessonRequest);
                });

        assertThat(exception.getErrorReasonHttpStatus())
                .isEqualTo(ErrorStatus.LESSONS_ALREADY_HAVE.getReasonHttpStatus());

        verify(lessonRepository, times(0)).save(any(Lesson.class));
        verify(userLessonRepository, times(0)).save(any(UserLesson.class));
    }

    @Test
    @DisplayName("[특정 수업 제거 성공]")
    void deleteUserLesson_success() {

        User user = new User();
        userRepository.save(user);
        Lesson lesson = new Lesson();
        lessonRepository.save(lesson);
        UserLesson userLesson = UserLesson.createUserLesson(user, lesson, "semester");
        userLessonRepository.save(userLesson);

        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));
        when(lessonRepository.findById(lesson.getId()))
                .thenReturn(Optional.of(lesson));
        when(userLessonRepository.findByUserAndAndAttendedSemesterAndAndLesson(
                user, lesson.getSemester(), lesson))
                .thenReturn(Optional.of(userLesson));

        assertThat(lessonService.deleteUserLesson(user.getId(), lesson.getId()))
                .isEqualTo(lesson.getId());
        verify(userLessonRepository, times(1)).deleteById(userLesson.getId());
    }

    @Test
    @DisplayName("[특정 수업 제거 실패] invalid userLessonId")
    void deleteUserLesson_fail_invalid_userLessonId() {
        User user = new User();
        userRepository.save(user);
        Lesson lesson = new Lesson();
        lessonRepository.save(lesson);

        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));
        when(lessonRepository.findById(lesson.getId()))
                .thenReturn(Optional.of(lesson));
        when(userLessonRepository.findByUserAndAndAttendedSemesterAndAndLesson(
                user, lesson.getSemester(), lesson))
                .thenReturn(Optional.empty());

        LessonException exception = assertThrows(
                LessonException.class, () -> {
                    lessonService.deleteUserLesson(user.getId(), lesson.getId());
                });

        assertThat(exception.getErrorReasonHttpStatus())
                .isEqualTo(ErrorStatus.USERLESSON_NOT_FOUND.getReasonHttpStatus());
    }

    @Test
    @DisplayName("[특정 수업 제거 실패] invalid userId")
    void deleteUserLesson_fail_invalid_userId() {
        User user = new User();
        userRepository.save(user);
        Lesson lesson = new Lesson();
        lessonRepository.save(lesson);

        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.empty());

        GeneralException exception = assertThrows(
                GeneralException.class, () -> {
                    lessonService.deleteUserLesson(user.getId(), lesson.getId());
                });

        assertThat(exception.getErrorReasonHttpStatus())
                .isEqualTo(ErrorStatus.USER_NOT_FOUND.getReasonHttpStatus());
    }

    @Test
    @DisplayName("[특정 수업 제거 실패] invalid lessonId")
    void deleteUserLesson_fail_invalid_lessonId() {
        User user = new User();
        userRepository.save(user);
        Lesson lesson = new Lesson();
        lessonRepository.save(lesson);

        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));
        when(lessonRepository.findById(lesson.getId()))
                .thenReturn(Optional.empty());

        LessonException exception = assertThrows(
                LessonException.class, () -> {
                    lessonService.deleteUserLesson(user.getId(), lesson.getId());
                });

        assertThat(exception.getErrorReasonHttpStatus())
                .isEqualTo(ErrorStatus.LESSON_NOT_FOUND.getReasonHttpStatus());
    }

}