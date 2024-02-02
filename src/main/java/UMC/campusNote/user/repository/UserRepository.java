package UMC.campusNote.user.repository;

import UMC.campusNote.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByClientId(String clientId);
}
