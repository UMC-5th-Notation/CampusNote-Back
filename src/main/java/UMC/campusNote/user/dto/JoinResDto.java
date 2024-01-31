package UMC.campusNote.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@Builder
public class JoinResDto {
    private String token;

    public static JoinResDto fromEntity(String token){
        return JoinResDto.builder()
                .token(token)
                .build();
    }
}
