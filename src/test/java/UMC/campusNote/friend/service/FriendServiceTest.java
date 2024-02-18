package UMC.campusNote.friend.service;

import UMC.campusNote.common.exception.GeneralException;
import UMC.campusNote.friend.converter.FriendConverter;
import UMC.campusNote.friend.dto.FriendRequestDTO;
import UMC.campusNote.friend.dto.FriendResponseDTO;
import UMC.campusNote.friend.entity.Friend;
import UMC.campusNote.friend.repository.FriendRepository;
import UMC.campusNote.user.entity.User;
import UMC.campusNote.user.repository.UserRepository;
import org.junit.jupiter.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
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

    //@BeforeEach
    //public void setUp(){
    //}

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

    @Test
    @DisplayName("[findFriendList service 성공] find friendList test")
    void findFriendListTest() {
        // Mock 데이터 설정
        Long userId=1L;
        User user1 = User.builder()
                .id(userId)
                .build();
        User user2 = User.builder()
                .id(2L)
                .build();
        User user3 = User.builder()
                .id(3L)
                .build();

        Friend friend1 = Friend.builder()
                .user1(user1)
                .user2(user2)
                .build();
        Friend friend2 = Friend.builder()
                .user1(user3)
                .user2(user1)
                .build();

        List<Friend> friends = new ArrayList<>();
        friends.add(friend1);
        friends.add(friend2);

        List<FriendResponseDTO.friendListDTO> expectedFriendList = new ArrayList<>();
        expectedFriendList.add(FriendConverter.toFriendListDTO(friend1.getUser2()));
        expectedFriendList.add(FriendConverter.toFriendListDTO(friend2.getUser1()));

        // userRepository의 findById 메소드 Mock 설정
        when(userRepository.findById(userId)).thenReturn(Optional.of(user1));

        // friendRepository의 findAllByUser1 메소드 Mock 설정
        when(friendRepository.findAllByUser1(user1)).thenReturn(friends);

        // 테스트 대상 메소드 호출
        List<FriendResponseDTO.friendListDTO> actualFriendList = friendService.findFriendList(userId);

        // 결과 검증
        Assertions.assertEquals(expectedFriendList.size(), actualFriendList.size());
        for (int i = 0; i < expectedFriendList.size(); i++) {
            FriendResponseDTO.friendListDTO expectedFriend = expectedFriendList.get(i);
            FriendResponseDTO.friendListDTO actualFriend = actualFriendList.get(i);
            Assertions.assertEquals(expectedFriend.getFriendId(), actualFriend.getFriendId());
            // 다른 필드에 대한 추가 검증 로직 작성
        }

        // userRepository의 findById 메소드가 정확히 1번 호출되었는지 검증
        verify(userRepository, times(1)).findById(userId);
        // friendRepository의 findAllByUser1 메소드가 정확히 1번 호출되었는지 검증
        verify(friendRepository, times(1)).findAllByUser1(user1);
    }
}
