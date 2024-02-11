package UMC.campusNote.auth.converter;

import UMC.campusNote.auth.dto.AuthRequestDTO;
import UMC.campusNote.auth.dto.AuthResponseDTO;
import UMC.campusNote.user.entity.User;

import static UMC.campusNote.user.entity.Role.USER;

public class AuthConverter {
    public static AuthResponseDTO.JoinResDTO toJoinResDto(Long userId, String accesstoken, String refreshtoken){
        return AuthResponseDTO.JoinResDTO.builder()
                .userId(userId)
                .accesstoken(accesstoken)
                .refreshtoken(refreshtoken)
                .build();
    }


    public static AuthResponseDTO.LoginResDTO toLoginResDto(Long userId, String accessToken, String refreshToken){
        return AuthResponseDTO.LoginResDTO.builder()
                .userId(userId)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    public static AuthResponseDTO.RefreshResDTO toRefreshResDto(String accessToken){
        return AuthResponseDTO.RefreshResDTO.builder()
                .accessToken(accessToken)
                .build();
    }

    public static User toUser(AuthRequestDTO.JoinReqDTO joinReqDto){
        return User.builder()
                .clientId(joinReqDto.getClientId())
                .img(joinReqDto.getImg())
                .name(joinReqDto.getName())
                .role(USER)
                .university(joinReqDto.getUniversity())
                .currentSemester(joinReqDto.getSemester())
                .build();
    }


}
