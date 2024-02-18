package UMC.campusNote.friend.service;

import UMC.campusNote.common.exception.GeneralException;
import UMC.campusNote.friend.converter.FriendConverter;
import UMC.campusNote.friend.dto.FriendRequestDTO;

import UMC.campusNote.friend.dto.FriendResponseDTO;
import UMC.campusNote.friend.entity.Friend;
import UMC.campusNote.friend.repository.FriendRepository;
import UMC.campusNote.user.entity.User;
import UMC.campusNote.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static UMC.campusNote.common.code.status.ErrorStatus.*;


@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    public void addFriend(FriendRequestDTO.AddFriendReqDTO addFriendReqDto) {

        Long inviterId = addFriendReqDto.getInviterUserId();
        Long invitedId = addFriendReqDto.getInvitedUserId();

        if (invitedId.equals(inviterId)) throw new GeneralException(FRIEND_NOT_MYSELF);

        User inviter = findUser(inviterId);
        User invited = findUser(invitedId);

        friendRepository.findByUser1AndUser2(inviter, invited)
                .ifPresent(friend1 -> {
                    throw new GeneralException(FRIEND_ALREADY_EXIST);
                });

        Friend friend = FriendConverter.fromEntity(invited, inviter);

        friendRepository.save(friend);
    }

    public List<FriendResponseDTO.friendListDTO> findFriendList(Long userId){
        User user = findUser(userId);

        List<Friend> friends = friendRepository.findAllByUser1(user);

        List<FriendResponseDTO.friendListDTO> friendList = friends.stream()
                .map((friend) -> {
                    if (friend.getUser1().equals(user)) return FriendConverter.toFriendListDTO(friend.getUser2());
                    else return FriendConverter.toFriendListDTO(friend.getUser1());
                })
                .toList();

        return friendList;
    }

    @Transactional
    public void deleteFriend(Long userId, Long friendId){
        User user = findUser(userId);
        User friend = findUser(friendId);

        friendRepository.deleteByUser1AndUser2(user, friend);
    }

    private User findUser(Long userId){
        return userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(USER_NOT_FOUND));
    }

}
