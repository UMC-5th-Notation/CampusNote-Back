package UMC.campusNote.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Builder
@Getter
@NoArgsConstructor
public class LoginResDto {
    private Long userId;
    private String accessToken;
    private String refreshToken;

    public static LoginResDto fromEntity(Long userId, String accessToken, String refreshToken){
        return LoginResDto.builder()
                .userId(userId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
