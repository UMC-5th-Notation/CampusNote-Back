package UMC.campusNote.mapping.repository;

import UMC.campusNote.lesson.entity.Lesson;
import UMC.campusNote.mapping.UserLesson;
import UMC.campusNote.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserLessonRepository extends JpaRepository<UserLesson, Long> {
    Optional<List<UserLesson>> findByUser(User user);

    Optional<List<UserLesson>> findByUserAndAttendedSemester(User user, String attendedSemester);

    Optional<UserLesson> findByUserAndAttendedSemesterAndLesson(User user, String attendedSemester, Lesson lesson);

    Optional<UserLesson> findByUserAndLesson(User user, Lesson lesson);

    @Modifying
    @Query("UPDATE UserLesson u SET u.attendedSemester = :attendedSemester WHERE u.user.id = :userId")
    int updateAttendedSemester(@Param("userId") Long userId, @Param("attendedSemester") String attendedSemester);

}
