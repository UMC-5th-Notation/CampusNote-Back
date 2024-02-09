package UMC.campusNote.friend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AddFriendReqDto {
    @NotNull
    private Long inviterUserId;
    @NotNull
    private Long invitedUserId;
}
