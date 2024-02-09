package UMC.campusNote.friend.service;

import UMC.campusNote.common.exception.GeneralException;
import UMC.campusNote.friend.dto.AddFriendReqDto;
import UMC.campusNote.friend.entity.Friend;
import UMC.campusNote.friend.repository.FriendRepository;
import UMC.campusNote.user.entity.User;
import UMC.campusNote.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import static UMC.campusNote.common.code.status.ErrorStatus.FRIEND_ALREADY_EXIST;
import static UMC.campusNote.common.code.status.ErrorStatus.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class FriendService {
    private final FriendRepository friendRepository;
    private final UserRepository userRepository;

    public void addFriend(AddFriendReqDto addFriendReqDto){
        User inviter = userRepository.findById(addFriendReqDto.getInviterUserId())
                .orElseThrow(() -> new GeneralException(USER_NOT_FOUND));
        User invited = userRepository.findById(addFriendReqDto.getInvitedUserId())
                .orElseThrow(() -> new GeneralException(USER_NOT_FOUND));

        friendRepository.findByUser1AndUser2(inviter, invited)
                .ifPresent(friend1 -> {
                    throw new GeneralException(FRIEND_ALREADY_EXIST);
                });

        Friend friend = Friend.fromEntity(invited, inviter);
        friendRepository.save(friend);
    }
}
