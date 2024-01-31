package UMC.campusNote.user.dto;

import UMC.campusNote.user.entity.User;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static UMC.campusNote.user.entity.Role.USER;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class JoinReqDto {
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

    public User toEntity(){
        return User.builder()
                .clientId(clientId)
                .img(img)
                .name(name)
                .role(USER.getRole())
                .university(university)
                .currentSemester(semester)
                .build();
    }

}
