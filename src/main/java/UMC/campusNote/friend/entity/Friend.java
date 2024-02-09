package UMC.campusNote.friend.entity;

import UMC.campusNote.common.BaseEntity;

import UMC.campusNote.user.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Friend extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FRIEND_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER1_ID")
    private User user1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER2_ID")
    private User user2;

    public static Friend fromEntity(User user1, User user2){
        return Friend.builder()
                .user1(user1)
                .user2(user2)
                .build();
    }
}
