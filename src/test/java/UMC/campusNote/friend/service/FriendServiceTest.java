package UMC.campusNote.friend.service;

import UMC.campusNote.common.exception.GeneralException;
import UMC.campusNote.friend.dto.FriendRequestDTO;
import UMC.campusNote.friend.entity.Friend;
import UMC.campusNote.friend.repository.FriendRepository;
import UMC.campusNote.user.entity.User;
import UMC.campusNote.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class FriendServiceTest {
    @Mock
    FriendRepository friendRepository;
    @Mock
    UserRepository userRepository;
    @InjectMocks
    FriendService friendService;

//    @BeforeEach
//    public void setUp(){
//
//    }

    @Test
    @DisplayName("[addFriend service 성공] Add friend test")
    public void addFriendTest(){
        // 테스트용 데이터 생성

        FriendRequestDTO.AddFriendReqDTO addFriendReqDto = new FriendRequestDTO.AddFriendReqDTO();

        addFriendReqDto.setInviterUserId(1L);
        addFriendReqDto.setInvitedUserId(2L);

        User inviter = new User();
        inviter.setId(1L);

        User invited = new User();
        invited.setId(2L);

        // userRepository mock 객체 설정
        when(userRepository.findById(1L)).thenReturn(Optional.of(inviter));
        when(userRepository.findById(2L)).thenReturn(Optional.of(invited));

        // friendRepository mock 객체 설정
        when(friendRepository.findByUser1AndUser2(inviter, invited)).thenReturn(Optional.empty());

        // 테스트 대상 메서드 호출
        friendService.addFriend(addFriendReqDto);

        // friendRepository.save() 메서드가 호출되었는지 검증
        verify(friendRepository, times(1)).save(any(Friend.class));

    }

    @Test
    @DisplayName("[addFriend service 실패] FRIEND_ALREADY_EXIST")
    public void addFriendTest_FRIEND_ALREADY_EXIST(){

        // 테스트용 데이터 생성
        FriendRequestDTO.AddFriendReqDTO addFriendReqDto = new FriendRequestDTO.AddFriendReqDTO();

        addFriendReqDto.setInviterUserId(1L);
        addFriendReqDto.setInvitedUserId(2L);

        User inviter = new User();
        inviter.setId(1L);

        User invited = new User();
        invited.setId(2L);

        // userRepository mock 객체 설정
        when(userRepository.findById(1L)).thenReturn(Optional.of(inviter));
        when(userRepository.findById(2L)).thenReturn(Optional.of(invited));

        // friendRepository mock 객체 설정
        Friend existingFriend = new Friend();
        when(friendRepository.findByUser1AndUser2(inviter, invited)).thenReturn(Optional.of(existingFriend));

        // 테스트 대상 메서드 호출 시 예외가 발생하는지 검증
        Assertions.assertThrows(GeneralException.class, () -> {
            friendService.addFriend(addFriendReqDto);
        });

        // friendRepository.save() 메서드가 호출되지 않았는지 검증
        verify(friendRepository, never()).save(any(Friend.class));
    }
}
