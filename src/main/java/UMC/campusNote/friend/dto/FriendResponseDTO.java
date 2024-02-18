package UMC.campusNote.friend.dto;

import lombok.*;

public class FriendResponseDTO {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    public static class friendListDTO{
        private Long friendId;
        private String name;
        private String img;
    }

}
