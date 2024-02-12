package UMC.campusNote.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class JoinResDto {
    private Long userId;
    private String accesstoken;
    private String refreshtoken;
    public static JoinResDto fromEntity(Long userId, String accesstoken, String refreshtoken){
        return JoinResDto.builder()
                .userId(userId)
                .accesstoken(accesstoken)
                .refreshtoken(refreshtoken)
                .build();
    }
}
