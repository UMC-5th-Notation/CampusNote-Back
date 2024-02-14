package UMC.campusNote.friend.service;

import UMC.campusNote.common.exception.GeneralException;

import UMC.campusNote.friend.converter.FriendConverter;
import UMC.campusNote.friend.dto.FriendRequestDTO;

import UMC.campusNote.friend.entity.Friend;
import UMC.campusNote.friend.repository.FriendRepository;
import UMC.campusNote.user.entity.User;
import UMC.campusNote.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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

        User inviter = userRepository.findById(inviterId)
                .orElseThrow(() -> new GeneralException(USER_NOT_FOUND));
        User invited = userRepository.findById(invitedId)
                .orElseThrow(() -> new GeneralException(USER_NOT_FOUND));

        friendRepository.findByUser1AndUser2(inviter, invited)
                .ifPresent(friend1 -> {
                    throw new GeneralException(FRIEND_ALREADY_EXIST);
                });


        Friend friend = FriendConverter.fromEntity(invited, inviter);

        friendRepository.save(friend);
    }
}
