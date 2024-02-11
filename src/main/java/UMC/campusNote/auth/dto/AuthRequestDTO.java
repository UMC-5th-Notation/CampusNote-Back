package UMC.campusNote.auth.dto;

import UMC.campusNote.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static UMC.campusNote.user.entity.Role.USER;

public class AuthRequestDTO {
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Builder
    public static class JoinReqDTO {
        @NotBlank
        @Size(max=100)
        private String clientId;
        @NotBlank
        @Size(max=20)
        private String name;
        @NotBlank
        @Size(max=20)
        private String university;
        @NotBlank
        @Size(max=10)
        private String semester;
        @NotBlank
        private String img;

    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Getter
    public static class LoginReqDTO {
        @NotBlank
        private String clientId;
    }

}
