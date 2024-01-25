package UMC.campusNote.mapping.repository;

import UMC.campusNote.mapping.UserLesson;
import UMC.campusNote.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserLessonRepository extends JpaRepository<UserLesson, Long> {
    Optional<List<UserLesson>> findByUser(User user);
}
