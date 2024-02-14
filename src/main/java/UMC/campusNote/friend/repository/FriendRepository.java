package UMC.campusNote.friend.repository;

import UMC.campusNote.friend.entity.Friend;
import UMC.campusNote.user.entity.User;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {
  
    @Query("SELECT f FROM Friend f WHERE (f.user1=:user1 AND f.user2=:user2) OR (f.user1=:user2 AND f.user2=:user1)")
    Optional<Friend> findByUser1AndUser2(@Param("user1") User user1, @Param("user2") User user2);

}
