package UMC.campusNote.auth.service;

import UMC.campusNote.auth.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    AuthResponseDTO.JoinResDTO join(AuthRequestDTO.JoinReqDTO joinReqDto);

    AuthResponseDTO.LoginResDTO login(AuthRequestDTO.LoginReqDTO loginReqDto);

    AuthResponseDTO.RefreshResDTO refreshToken(HttpServletRequest request, HttpServletResponse response);

}
