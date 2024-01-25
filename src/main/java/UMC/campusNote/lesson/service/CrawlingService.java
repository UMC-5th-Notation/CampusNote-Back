package UMC.campusNote.lesson.service;

import UMC.campusNote.lesson.entity.Lesson;
import UMC.campusNote.lesson.repository.LessonRepository;
import UMC.campusNote.mapping.UserLesson;
import UMC.campusNote.mapping.repository.UserLessonRepository;
import UMC.campusNote.user.User;
import UMC.campusNote.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static UMC.campusNote.lesson.converter.CrawlingHelper.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class CrawlingService {

    private final LessonRepository lessonRepository;
    private final UserRepository userRepository;
    private final UserLessonRepository userLessonRepository;

    @Transactional
    public List<UserLesson> action(String url) {

        log.info("enter CrawlingService");

        // setupCode
        System.setProperty("webdriver.chrome.driver", "./chromedriver");
        WebDriver driver = new ChromeDriver();
        driver.get(url);

        /* semester 탈취 */
        String semester = getSemester(driver);
        User user = userRepository.findById(1L).get(); // test 용도 member 생성
        String university = "인하대학교"; // test 용도 학교명 고정


        // 이미 기존에 저장되어있는 정보가 존재하는 경우 예외처리
        //Q. 기존에 등록된 금학기 정보가 존재하면 어떻게 처리?
        if (!user.getUserLessonList().isEmpty()) {

            // 어떻게 처리할 것인지 정하기
            // 현재 코드 : 모두 지우고 생성
            Optional<List<UserLesson>> byMember = userLessonRepository.findByUser(user);
            if (byMember.isPresent()) {
                for (UserLesson memberLesson : byMember.get()) {
                    memberLesson.getLesson().getUserLessonList().clear();
                    memberLesson.getUser().getUserLessonList().clear();
                    userLessonRepository.delete(memberLesson);
                }
            }
        }

        /* crawling logic */
        crawl(driver, semester, user, university);

        // WebDriver 종료
        driver.quit();

        /* get result */
        // 여기서 어차피 과목별 시간으로 보내주는게 좋을 것 같긴함
        List<UserLesson> memberLessonList = user.getUserLessonList();
        return memberLessonList;
    }

    public String getSemester(WebDriver driver) {

        WebElement menu = driver.findElement(By.cssSelector(".menu"));
        String hamburger_outer = menu.getAttribute("outerHTML");
        Document menu_doc = org.jsoup.Jsoup.parse(hamburger_outer);
        Elements activeSemester = menu_doc.select(".active"); // .cols -> 각 열 추출

        return activeSemester.text();//hamburger_doc.text();
    }

    public void crawl(WebDriver driver, String semester, User user, String university) {
        /* crawling logic */
        WebElement tableBody = driver.findElement(By.cssSelector(".tablebody"));
        String outer = tableBody.getAttribute("outerHTML");
        Document doc = org.jsoup.Jsoup.parse(outer); // HTML 파싱
        Elements table_cols = doc.select(".cols"); // .cols -> 각 열 추출

        int days_idx = 0;
        String[] days = new String[]{"월", "화", "수", "목", "금", "토", "일"};
        for (Element table_col : table_cols) {

            String dayOfWeek = days[days_idx];

            String col_html = table_col.html();
            Document document = org.jsoup.Jsoup.parse(col_html);
            Elements elements = document.select("div.subject");

            for (Element e : elements) {

                double startTime = topToStartClock(getTop(e));
                double runningTime = heightToRunningTime(getHeight(e));
                List<String> contents = getContents(e);

                String lessonName = contents.get(0);
                String professorName = contents.get(1);
                String location = contents.get(2);

                // 고유한 강의 찾기
                Optional<Lesson> findLesson = lessonRepository
                        .findByUniversityAndSemesterAndLessonNameAndProfessorNameAndLocationAndStartTimeAndRunningTimeAndDayOfWeek(
                                university, semester, lessonName, professorName, location, startTime, runningTime, dayOfWeek
                        );

                if (findLesson.isEmpty()) {

                    Lesson lesson = Lesson.builder()
                            .university(university)
                            .semester(semester)
                            .lessonName(lessonName)
                            .professorName(professorName)
                            .location(location)
                            .startTime(startTime)
                            .runningTime(runningTime)
                            .dayOfWeek(dayOfWeek)
                            .build();
                    lessonRepository.save(lesson);

                    UserLesson memberLesson = new UserLesson();
                    memberLesson.setUserAndLesson(user, lesson); // 연관관계 편의 메소드 사용
                    userLessonRepository.save(memberLesson);

                } else {

                    UserLesson memberLesson = new UserLesson();
                    memberLesson.setUserAndLesson(user, findLesson.get());
                    userLessonRepository.save(memberLesson);
                }
            }
            days_idx++;
        }
    }

}
