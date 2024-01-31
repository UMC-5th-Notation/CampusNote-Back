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
    private String accessToken;
    private String refreshToken;

    public static LoginResDto fromEntity(String accessToken, String refreshToken){
        return LoginResDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
