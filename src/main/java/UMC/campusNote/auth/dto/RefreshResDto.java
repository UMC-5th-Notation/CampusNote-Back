package UMC.campusNote.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RefreshResDto {

    private String accessToken;

    public static RefreshResDto fromEntity(String accessToken){
        return RefreshResDto.builder()
                .accessToken(accessToken)
                .build();
    }
}
