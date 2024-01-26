package UMC.campusNote.lesson.repository;

import UMC.campusNote.lesson.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    // 쿼리 튜닝 필요
    Optional<Lesson> findByUniversityAndSemesterAndLessonNameAndProfessorNameAndLocationAndStartTimeAndRunningTimeAndDayOfWeek(
            String university, String semester, String lessonName, String professorName, String location,
            String startTime, String runningTime, String dayOfWeek
    );
}
