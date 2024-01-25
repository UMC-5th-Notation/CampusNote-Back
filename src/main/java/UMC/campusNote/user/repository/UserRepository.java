package UMC.campusNote.user.repository;

import UMC.campusNote.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
