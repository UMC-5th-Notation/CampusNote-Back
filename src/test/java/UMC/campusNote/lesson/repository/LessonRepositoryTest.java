package UMC.campusNote.lesson.repository;

import UMC.campusNote.lesson.entity.Lesson;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class LessonRepositoryTest {

    @Autowired
    LessonRepository lessonRepository;

    Lesson createFirstLesson() {

        String university = "인하대학교";
        String semester = "2023년 2학기";
        String lessonName = "객체지향프로그래밍 2";
        String professorName = "이연";
        String location = "하-322";
        String startTime = "9.0";
        String runningTime = "2.0";
        String dayOfWeek = "월";

        return Lesson.createLesson(university, semester, lessonName, professorName, location,
                startTime, runningTime, dayOfWeek);
    }

    @Transactional
    @Test
    @DisplayName("[강의 생성 성공]")
    void createLesson_success() {

        Lesson lesson = createFirstLesson();

        lessonRepository.save(lesson);

        assertThat(lessonRepository.findById(lesson.getId()).get())
                .isEqualTo(lesson);
    }

    @Transactional
    @Test
    @DisplayName("[유일한 강의 찾기 성공] : using several params")
    void findUniqueLesson_success_params() {

        Lesson lesson = createFirstLesson();

        lessonRepository.save(lesson);

        Lesson foundLesson = lessonRepository.findUniqueLesson(lesson.getUniversity(), lesson.getSemester(),
                lesson.getLessonName(), lesson.getProfessorName(), lesson.getLocation(),
                lesson.getStartTime(), lesson.getRunningTime(), lesson.getDayOfWeek()).get();

        assertThat(foundLesson)
                .isEqualTo(lesson);
    }

    @Transactional
    @Test
    @DisplayName("[유일한 강의 찾기 실패] invalid several param")
    void findUniqueLesson_fail_params() {

        Lesson lesson = createFirstLesson();

        lessonRepository.save(lesson);

        Lesson foundLesson = lessonRepository.findUniqueLesson("인천대학교", lesson.getSemester(),
                lesson.getLessonName(), lesson.getProfessorName(), lesson.getLocation(),
                lesson.getStartTime(), lesson.getRunningTime(), lesson.getDayOfWeek()).orElse(null);

        assertThat(foundLesson)
                .isEqualTo(null);
    }

    @Transactional
    @Test
    @DisplayName("[유일한 강의 찾기 성공] : using entity param")
    void findUniqueLesson_success_byEntity() {

        Lesson lesson = createFirstLesson();

        lessonRepository.save(lesson);

        assertThat(lessonRepository.findUniqueLesson(lesson).get())
                .isEqualTo(lesson);
    }

    @Transactional
    @Test
    @DisplayName("[유일한 강의 찾기 실패] invalid entity param")
    void findUniqueLesson_fail_byEntity() {

        Lesson firstLesson = createFirstLesson();
        Lesson secondLesson = new Lesson();

        lessonRepository.save(firstLesson);

        Lesson foundLesson = lessonRepository.findUniqueLesson(secondLesson).orElse(null);

        assertThat(foundLesson)
                .isNull();
    }
}