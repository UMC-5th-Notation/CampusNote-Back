package UMC.campusNote.friend.converter;

import UMC.campusNote.friend.dto.FriendResponseDTO;
import UMC.campusNote.friend.entity.Friend;
import UMC.campusNote.user.entity.User;

public class FriendConverter {
    public static Friend fromEntity(User user1, User user2){
        return Friend.builder()
                .user1(user1)
                .user2(user2)
                .build();
    }

    public static FriendResponseDTO.friendListDTO toFriendListDTO(User user){
        return FriendResponseDTO.friendListDTO.builder()
                .friendId(user.getId())
                .img(user.getImg())
                .name(user.getName())
                .build();
    }
}
