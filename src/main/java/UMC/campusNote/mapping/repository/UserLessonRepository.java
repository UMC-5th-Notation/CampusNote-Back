package UMC.campusNote.mapping.repository;

import UMC.campusNote.lesson.entity.Lesson;
import UMC.campusNote.mapping.UserLesson;
import UMC.campusNote.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserLessonRepository extends JpaRepository<UserLesson, Long> {
    Optional<List<UserLesson>> findByUser(User user);

    Optional<List<UserLesson>> findByUserAndAndAttendedSemester(User user, String attendedSemester);

    Optional<UserLesson> findByUserAndAndAttendedSemesterAndAndLesson(User user, String attendedSemester, Lesson lesson);
}
