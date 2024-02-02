package UMC.campusNote.user;

import UMC.campusNote.common.BaseEntity;
import UMC.campusNote.friend.Friend;
import UMC.campusNote.mapping.UserLesson;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(length = 20)
    private String name;

    @Column(length = 20)
    private String university;

    private String img; // 프로필 이미지

    @ColumnDefault("true")
    private boolean status;

    @Column(length = 10)
    private String currentSemester;

    @Column(length = 10)
    private String role;

    @OneToMany(mappedBy = "user1", cascade = CascadeType.ALL)
    @Builder.Default
    List<Friend> friendList1 = new ArrayList<>();

    @OneToMany(mappedBy = "user2", cascade = CascadeType.ALL)
    @Builder.Default
    List<Friend> friendList2 = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @Builder.Default
    List<UserLesson> userLessonList = new ArrayList<>();
}
