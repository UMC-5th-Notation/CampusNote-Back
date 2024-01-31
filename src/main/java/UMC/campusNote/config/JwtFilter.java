package UMC.campusNote.config;

import UMC.campusNote.user.service.UserService;
import UMC.campusNote.user.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.swing.*;
import java.io.IOException;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    private final UserService userService;
    private final JwtUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {

            String token = resolveToken(request);

            //Token validation 여부
            if (token==null||!jwtUtil.validToken(token)) {
                log.error("token이 유효하지 않습니다: {}",token);
                filterChain.doFilter(request, response);
                return;
            }

            //UserName Token에서 꺼내기
            String userName = jwtUtil.getUserName(token);
            log.info("userName:{}", userName);

            //권한 부여 => 수정 필요
            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(userName, null, List.of(new SimpleGrantedAuthority("USER")));
            //Detail을 넣어준다.
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        } catch (Exception exception) {
            exception.printStackTrace();
        }

        filterChain.doFilter(request, response);
    }

    private String resolveToken(HttpServletRequest request){

        String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);

        // token 안 보내면 Block
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return null;
        }

        //Token 꺼내기
        return authorization.split(" ")[1];
    }

}
