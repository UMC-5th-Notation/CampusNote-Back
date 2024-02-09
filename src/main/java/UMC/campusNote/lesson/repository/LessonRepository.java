package UMC.campusNote.lesson.repository;

import UMC.campusNote.lesson.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    //    @Query("SELECT l FROM Lesson l " +
//            "WHERE l.id IN (" +
//            "    SELECT ls.id FROM Lesson ls " +
//            "    WHERE ls.university = :university " +
//            "    AND ls.semester = :semester " +
//            "    AND ls.lessonName = :lessonName" +
//            ") " +
//            "AND l.professorName = :professorName " +
//            "AND l.location = :location " +
//            "AND l.startTime = :startTime " +
//            "AND l.runningTime = :runningTime " +
//            "AND l.dayOfWeek = :dayOfWeek")
    @Query("SELECT l FROM Lesson l " +
            "WHERE l.university = :university " +
            "AND l.semester = :semester " +
            "AND l.lessonName = :lessonName " +
            "AND l.professorName = :professorName " +
            "AND l.location = :location " +
            "AND l.startTime = :startTime " +
            "AND l.runningTime = :runningTime " +
            "AND l.dayOfWeek = :dayOfWeek")
    Optional<Lesson> findUniqueLesson(
            @Param("university") String university,
            @Param("semester") String semester,
            @Param("lessonName") String lessonName,
            @Param("professorName") String professorName,
            @Param("location") String location,
            @Param("startTime") String startTime,
            @Param("runningTime") String runningTime,
            @Param("dayOfWeek") String dayOfWeek
    );

    @Query("SELECT l FROM Lesson l " +
            "WHERE l.university = :#{#lesson.university} " +
            "AND l.semester = :#{#lesson.semester} " +
            "AND l.lessonName = :#{#lesson.lessonName} " +
            "AND l.professorName = :#{#lesson.professorName} " +
            "AND l.location = :#{#lesson.location} " +
            "AND l.startTime = :#{#lesson.startTime} " +
            "AND l.runningTime = :#{#lesson.runningTime} " +
            "AND l.dayOfWeek = :#{#lesson.dayOfWeek}")
    Optional<Lesson> findUniqueLesson(@Param("lesson") Lesson lesson);


    //테스트용
    Optional<Lesson> findByLessonName(String lessonName);
}
