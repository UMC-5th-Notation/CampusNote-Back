package UMC.campusNote.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class JoinResDto {
    private String accesstoken;
    private String refreshtoken;
    public static JoinResDto fromEntity(String accesstoken, String refreshtoken){
        return JoinResDto.builder()
                .accesstoken(accesstoken)
                .refreshtoken(refreshtoken)
                .build();
    }
}
