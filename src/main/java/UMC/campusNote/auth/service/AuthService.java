package UMC.campusNote.auth.service;

import UMC.campusNote.auth.dto.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {


    JoinResDto join(JoinReqDto joinReqDto);

    LoginResDto login(LoginReqDto loginReqDto);

    RefreshResDto refreshToken(HttpServletRequest request, HttpServletResponse response);
}
