package UMC.campusNote.auth.jwt;

import UMC.campusNote.auth.redis.RedisProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import static UMC.campusNote.auth.jwt.JwtProvider.HEADER_AUTHORIZATION;
import static UMC.campusNote.auth.jwt.JwtProvider.TOKEN_PREFIX;


@Service
@RequiredArgsConstructor
@Slf4j
public class LogoutService implements LogoutHandler {

  private final JwtProvider jwtProvider;
  private final RedisProvider redisProvider;

  @Override
  public void logout(
      HttpServletRequest request,
      HttpServletResponse response,
      Authentication authentication
  ) {
    final String authHeader = request.getHeader(HEADER_AUTHORIZATION);
    final String jwt;
    if (authHeader == null ||!authHeader.startsWith(TOKEN_PREFIX)) {
      return;
    }
    jwt = authHeader.substring(7);
    String username = jwtProvider.extractUsername(jwt);
    log.info("username: {}", username);
    redisProvider.deleteValueOps(username);
      SecurityContextHolder.clearContext();
    }
}
