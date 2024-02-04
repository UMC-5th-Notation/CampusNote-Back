//package UMC.campusNote.lesson;
//
//import UMC.campusNote.user.entity.User;
//import UMC.campusNote.user.repository.UserRepository;
//import jakarta.annotation.PostConstruct;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Component;
//
//@Component
//@RequiredArgsConstructor
//public class TestDataInit {
//    private final UserRepository userRepository;
//
//    /**
//     * 테스트용 데이터 추가
//     */
//    @PostConstruct
//    public void init() {
//        User user = User.builder()
//                .university("인하대학교")
//                .build();
//        userRepository.save(user);
//    }
//
//}