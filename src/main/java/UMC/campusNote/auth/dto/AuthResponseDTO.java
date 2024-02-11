package UMC.campusNote.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

public class AuthResponseDTO {
    @AllArgsConstructor
    @Getter
    @Builder
    public static class JoinResDTO {
        private Long userId;
        private String accesstoken;
        private String refreshtoken;

    }

    @AllArgsConstructor
    @Builder
    @Getter
    @NoArgsConstructor
    public static class LoginResDTO {
        private Long userId;
        private String accessToken;
        private String refreshToken;

    }

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    public static class RefreshResDTO {
        private String accessToken;
    }
}
