package UMC.campusNote.lesson.service;

import UMC.campusNote.common.code.status.ErrorStatus;
import UMC.campusNote.lesson.entity.Lesson;
import UMC.campusNote.lesson.repository.LessonRepository;
import UMC.campusNote.lesson.exception.CrawlingException;
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
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CrawlingServiceTest {

    @InjectMocks
    CrawlingService crawlingService;
    @Mock
    UserRepository userRepository;
    @Mock
    UserLessonRepository userLessonRepository;
    @Mock
    LessonRepository lessonRepository;

    String success_url = "https://everytime.kr/@zjmhATXF5czcDHm78zDZ";
    String fail_url_empty = "";
    String fail_url_invalid = "invalid url";
    String fail_url_driver = "https://.kr/@zjmhATXF5czcDHm78zDZ";


    @Test
    @DisplayName("[url 유효성 검증 성공]")
    void validateUrl_success() {

        System.setProperty("webdriver.chrome.driver", "./chromedriver");
        WebDriver driver = new ChromeDriver();

        assertDoesNotThrow(() -> {
            crawlingService.isValidateUrl(driver, success_url);
        });

        driver.close();
    }

    @Test
    @DisplayName("[url 유효성 검증 실패] empty url")
    void validateUrl_fail_empty_url() {

        System.setProperty("webdriver.chrome.driver", "./chromedriver");

        WebDriver driver = new ChromeDriver();
        CrawlingException exception = assertThrows(
                CrawlingException.class, () -> {
                    crawlingService.isValidateUrl(driver, fail_url_empty);
                });

        assertThat(exception.getErrorReasonHttpStatus())
                .isEqualTo(ErrorStatus.CRAWLING_URL_INVALID.getReasonHttpStatus());
    }

    @Test
    @DisplayName("[url 유효성 검증 실패] invalid url not http,,,")
    void validateUrl_fail_invalid_url() {

        System.setProperty("webdriver.chrome.driver", "./chromedriver");

        WebDriver driver = new ChromeDriver();
        CrawlingException exception = assertThrows(
                CrawlingException.class, () -> {
                    crawlingService.isValidateUrl(driver, fail_url_invalid);
                });

        assertThat(exception.getErrorReasonHttpStatus())
                .isEqualTo(ErrorStatus.CRAWLING_URL_INVALID.getReasonHttpStatus());
    }

    @Test
    @DisplayName("[url 유효성 검증 실패] invalid url just missing sth")
    void validateUrl_fail_driver_url() {

        System.setProperty("webdriver.chrome.driver", "./chromedriver");

        WebDriver driver = new ChromeDriver();
        CrawlingException exception = assertThrows(
                CrawlingException.class, () -> {
                    crawlingService.isValidateUrl(driver, fail_url_driver);
                });

        assertThat(exception.getErrorReasonHttpStatus())
                .isEqualTo(ErrorStatus.CRAWLING_URL_INVALID.getReasonHttpStatus());
    }


    @Test
    @DisplayName("[saveUserAndUserLesson] isEmpty case")
    void saveUserAndUserLesson_isEmpty() {

        User user = new User();
        Lesson lesson = new Lesson();
        boolean isSet = true;

        when(lessonRepository.findUniqueLesson(lesson)).thenReturn(Optional.empty());

        crawlingService.saveUserAndUserLesson(user, lesson, isSet);

        verify(lessonRepository, times(1)).save(lesson);
        verify(userLessonRepository, times(1)).save(any(UserLesson.class));
    }

    @Test
    @DisplayName("[saveUserAndUserLesson] isPresent and isNotSet case")
    void saveUserAndUserLesson_isPresent_isNotSet() {

        User user = new User();
        Lesson lesson = new Lesson();
        boolean isSet = false;

        when(lessonRepository.findUniqueLesson(lesson)).thenReturn(Optional.of(lesson));

        crawlingService.saveUserAndUserLesson(user, lesson, isSet);

        verify(userLessonRepository, times(1)).save(any(UserLesson.class));
    }

    @Test
    @DisplayName("[saveUserAndUserLesson] isPresent and isSet and isDup case")
    void saveUserAndUserLesson_isPresent_isSet_isDup() {

        User user = new User();
        Lesson lesson = Lesson.builder().semester("semester").build();
        boolean isSet = true;

        when(lessonRepository.findUniqueLesson(lesson)).thenReturn(Optional.of(lesson));
        when(userLessonRepository.findByUserAndAttendedSemesterAndLesson(user, lesson.getSemester(), lesson))
                .thenReturn(Optional.of(new UserLesson())); // isDup

        assertThat(crawlingService.saveUserAndUserLesson(user, lesson, isSet)).isNull();
    }

    @Test
    @DisplayName("[saveUserAndUserLesson] isPresent and isSet and isNotDup case")
    void saveUserAndUserLesson_isPresent_isSet_isNotDup() {

        User user = new User();
        Lesson lesson = new Lesson();
        boolean isSet = true;

        when(lessonRepository.findUniqueLesson(lesson)).thenReturn(Optional.of(lesson));
        when(userLessonRepository.findByUserAndAttendedSemesterAndLesson(user, lesson.getSemester(), lesson))
                .thenReturn(Optional.empty());

        crawlingService.saveUserAndUserLesson(user, lesson, isSet);

        verify(userLessonRepository, times(1)).save(any(UserLesson.class));
    }

    @Test
    @DisplayName("[크롤링 성공]")
    void action_success() {

        User user = new User();
        userRepository.save(user);

        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));
        assertDoesNotThrow(() -> {
            crawlingService.action(success_url, user.getId());
        });

        // success url case : 11 lessons saved
        verify(lessonRepository, times(11)).save(any(Lesson.class));
        verify(userLessonRepository, times(11)).save(any(UserLesson.class));
    }

    @Test
    @DisplayName("[크롤링 실패] invalid url")
    void action_fail_invalid_url() {

        User user = new User();
        userRepository.save(user);

        when(userRepository.findById(user.getId()))
                .thenReturn(Optional.of(user));
        CrawlingException exception = assertThrows(
                CrawlingException.class, () -> {
                    crawlingService.action(fail_url_invalid, user.getId());
                });

        assertThat(exception.getErrorReasonHttpStatus())
                .isEqualTo(ErrorStatus.CRAWLING_URL_INVALID.getReasonHttpStatus());
    }
}