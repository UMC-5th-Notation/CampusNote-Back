package UMC.campusNote.auth.service;

import UMC.campusNote.auth.converter.AuthConverter;
import UMC.campusNote.auth.dto.*;
import UMC.campusNote.auth.jwt.JwtProvider;
import UMC.campusNote.auth.redis.RedisProvider;
import UMC.campusNote.common.exception.GeneralException;
import UMC.campusNote.user.entity.User;
import UMC.campusNote.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static UMC.campusNote.auth.jwt.JwtProvider.HEADER_AUTHORIZATION;
import static UMC.campusNote.auth.jwt.JwtProvider.TOKEN_PREFIX;
import static UMC.campusNote.common.code.status.ErrorStatus.USER_ALREADY_EXIST;
import static UMC.campusNote.common.code.status.ErrorStatus.USER_NOT_FOUND;


@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final RedisProvider redisProvider;

    @Override
    @Transactional
    public AuthResponseDTO.JoinResDTO join(AuthRequestDTO.JoinReqDTO joinReqDto) {
        userRepository.findByClientId(joinReqDto.getClientId())
                .ifPresent( user -> {
                    throw new GeneralException(USER_ALREADY_EXIST);
                });
        User user = AuthConverter.toUser(joinReqDto);
        User savedUser = userRepository.save(user);
        String accessToken = jwtProvider.generateToken(user);
        String refreshToken = jwtProvider.generateRefreshToken(user);
        saveUserToken(savedUser, refreshToken);
        return AuthConverter.toJoinResDTO(savedUser.getId(), accessToken, refreshToken);
    }

    @Override
    @Transactional
    public AuthResponseDTO.LoginResDTO login(AuthRequestDTO.LoginReqDTO loginReqDto) {
        //log.info("loginReqDto.getClientId() : {}", loginReqDto.getClientId());
        User user = userRepository.findByClientId(loginReqDto.getClientId())
                .orElseThrow(() -> new GeneralException(USER_NOT_FOUND));
        String accessToken = jwtProvider.generateToken(user);
        String refreshToken = jwtProvider.generateRefreshToken(user);
        revokeAllUserTokens(user);
        saveUserToken(user, refreshToken);
        return AuthConverter.toLoginResDTO(user.getId(), accessToken, refreshToken);
    }

    @Override
    public AuthResponseDTO.RefreshResDTO refreshToken(HttpServletRequest request, HttpServletResponse response) {
        final String authHeader = request.getHeader(HEADER_AUTHORIZATION);
        final String refreshToken;
        final String userEmail;
        if (authHeader == null || !authHeader.startsWith(TOKEN_PREFIX))
            return null;
        refreshToken = authHeader.substring(7);
        userEmail = jwtProvider.extractUsername(refreshToken);
        if (userEmail != null) {
            User user = userRepository.findByClientId(userEmail)
                    .orElseThrow(() -> new GeneralException(USER_NOT_FOUND));
            if (jwtProvider.isTokenValid(refreshToken, user)) {
                String accessToken = jwtProvider.generateToken(user);
                return AuthConverter.toRefreshResDTO(accessToken);
            }
        }
        return null;
    }

    private void saveUserToken(User user, String refreshToken) {
        //key는 사용자 이메일과 토큰 발급 시간으로 구성 // 추후에 발급 시간이 아닌 기기로 구분하는 거로 수정해야함
        //redisService.setValueOps(user.getEmail() + ":" + issuedAt, refreshToken);
        log.info("user.getClientId() : {}", user.getClientId());
        redisProvider.setValueOps(user.getClientId(), refreshToken);
        redisProvider.expireValues(user.getClientId());
    }


    private void revokeAllUserTokens(User user) {
        redisProvider.deleteValueOps(user.getClientId());
    }


}
