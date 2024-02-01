package UMC.campusNote.lesson.service;

import UMC.campusNote.common.code.status.ErrorStatus;
import UMC.campusNote.common.exception.GeneralException;
import UMC.campusNote.lesson.converter.LessonConverter;
import UMC.campusNote.lesson.dto.LessonDto;
import UMC.campusNote.lesson.entity.Lesson;
import UMC.campusNote.lesson.repository.LessonRepository;
import UMC.campusNote.lesson.exception.CrawlingException;
import UMC.campusNote.mapping.UserLesson;
import UMC.campusNote.mapping.repository.UserLessonRepository;
import UMC.campusNote.user.entity.User;
import UMC.campusNote.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static UMC.campusNote.lesson.converter.CrawlingHelper.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class CrawlingService {

    private final LessonRepository lessonRepository;
    private final UserLessonRepository userLessonRepository;
    private final UserRepository userRepository;

    private final String[] days = new String[]{"월", "화", "수", "목", "금", "토", "일"};

    public void isValidateUrl(WebDriver driver, String url) {
        try {
            driver.get(url);
        } catch (WebDriverException e) {
            driver.close();
            throw new CrawlingException(ErrorStatus.CRAWLING_URL_INVALID);
        }
    }

    public WebDriver setUp() {
        System.setProperty("webdriver.chrome.driver", "./chromedriver");
        return new ChromeDriver();
    }

    @Transactional
    public List<LessonDto> action(String url, Long userId) {

        // setUp
        WebDriver driver = setUp();

        // userId 예외처리
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(UMC.campusNote.common.code.status.ErrorStatus.USER_NOT_FOUND));

        // url 예외처리
        isValidateUrl(driver, url);

        // crawling logic, get result
        try {
            return LessonConverter.userLessonsToLessonDtos(doCrawl(driver, user));
        } catch (WebDriverException e) {
            throw new CrawlingException(ErrorStatus.CRAWLING_URL_INVALID);
        } finally {
            driver.close();
        }
    }

    public List<UserLesson> doCrawl(WebDriver driver, User user) {

        // extract const value
        Map<String, String> result = extractConstValue(driver.findElement(By.cssSelector(".menu")), user);
        String semester = result.get("semester");
        String university = result.get("university");

        // dup check
        boolean isSet = userLessonRepository.findByUserAndAndAttendedSemester(user, semester)
                .isPresent();

        // extract Surround html code
        Elements table_cols = extractOuterHtml(driver);

        int days_idx = 0;
        for (Element table_col : table_cols) {
            String dayOfWeek = days[days_idx];

            // extract Inner html code
            Elements elements = extractInnerHtml(table_col);

            // get params and save
            getParamsAndSave(user, elements, university, semester, dayOfWeek, isSet);

            days_idx++;
        }

        return user.getUserLessonList();
    }

    public void getParamsAndSave(User user, Elements elements, String university, String semester, String dayOfWeek, boolean isSet) {
        for (Element e : elements) {
            // extract params
            String startTime = topToStartClock(getTop(e));
            String runningTime = heightToRunningTime(getHeight(e));
            Map<String, String> contents = getContents(e);
            String lessonName = contents.get("lessonName");
            String professorName = contents.get("professorName");
            String location = contents.get("location");

            Lesson lesson = Lesson.createLesson(
                    university, semester, lessonName, professorName, location, startTime, runningTime, dayOfWeek);

            saveUserAndUserLesson(user, lesson, isSet);
        }
    }

    public UserLesson saveUserAndUserLesson(User user, Lesson lesson, Boolean isSet) {
        // 고유한 강의 찾기
        Optional<Lesson> findLesson = lessonRepository.findUniqueLesson(lesson);

        if (findLesson.isEmpty()) {
            // 완전히 새로운 강의
            return findLessonIsEmpty(user, lesson);
        }
        if (findLesson.isPresent()) {
            return findLessonIsPresent(user, lesson.getSemester(), isSet, findLesson.get());
        }

        return null;
    }

    private UserLesson findLessonIsEmpty(User user, Lesson lesson) {
        lessonRepository.save(lesson);
        UserLesson userLesson = UserLesson.createUserLesson(user, lesson, lesson.getSemester());
        userLessonRepository.save(userLesson);
        return userLesson;
    }

    public UserLesson findLessonIsPresent(User user, String semester, boolean isSet, Lesson findLesson) {
        if (isSet) {
            // 존재하는 강의인데, 유저가 이미 들고있다면? 추가로 추가할 필요 없음
            Boolean isDup = userLessonRepository.findByUserAndAndAttendedSemesterAndAndLesson(user, semester, findLesson).isPresent();

            if (isDup) {
                // 존재하는강의 && 유저가 들고있는 경우
                return null;
            }
            if (!isDup) {
                // 존재하는 강의인데 유저가 안들고있는 경우
                UserLesson userLesson = UserLesson.createUserLesson(user, findLesson, semester);
                userLessonRepository.save(userLesson);
                return userLesson;
            }
        }
        if (!isSet) {
            // 금학기 수업정보가 아무것도 없을때
            UserLesson userLesson = UserLesson.createUserLesson(user, findLesson, semester);
            userLessonRepository.save(userLesson);
            return userLesson;
        }
        return null;
    }

}
