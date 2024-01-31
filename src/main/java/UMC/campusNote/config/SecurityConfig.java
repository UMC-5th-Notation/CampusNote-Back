package UMC.campusNote.config;

import UMC.campusNote.user.service.OAuth2UserService;
import UMC.campusNote.user.service.UserService;
import UMC.campusNote.user.utils.JwtUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.http.MediaType;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.annotation.web.configurers.HttpBasicConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.io.IOException;


@Slf4j
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final OAuth2UserService oAuth2UserService;
    private final UserService userService;
    private final JwtUtil jwtUtil;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.httpBasic(HttpBasicConfigurer::disable)
                .csrf(CsrfConfigurer::disable)
                .cors(cors->cors.configurationSource(corsConfigurationSource()))
                .sessionManagement(config -> config
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS) //jwt 사용하는 경우 씀
                );

        //권한에 따라 다르게
        http.authorizeRequests()
                .requestMatchers("/api/v1/users/join", "/api/v1/users/login","/login", "/oauth2/**", "/api/vi/auth/**").permitAll()
                .requestMatchers("/api/v1/user/**").hasRole("USER")
                .requestMatchers("/api/v1/admin/**").hasRole("ADMIN")
                .requestMatchers("/api/v1/**").authenticated()
                .anyRequest().permitAll();

        //authorizeRequests에서 발생한 Exception 처리
        http.exceptionHandling(exceptionHandling-> exceptionHandling
                .authenticationEntryPoint(new FailedAuthenticationEntryPoint())
        );

        //filter 적용
        http.addFilterBefore(new JwtFilter(userService, jwtUtil), UsernamePasswordAuthenticationFilter.class);


        //로그아웃 기능 추가 필요
        http.oauth2Login(oauth2Configurer->oauth2Configurer
                .authorizationEndpoint(authorizationEndpointConfig -> authorizationEndpointConfig.baseUri("/api/vi/auth/oauth2")) //default는 /oauth2/authorization, 접근하고 싶으면 baseUri 뒤에 registerId 넣어야 함
                .successHandler(successHandler())
                .failureHandler((request,response,exception)-> {
                    response.sendRedirect("/api/oauth2/failure");
                })
                .userInfoEndpoint(userInfoEndpointConfig -> userInfoEndpointConfig.userService(oAuth2UserService)))
                .logout(logoutConfigurer -> logoutConfigurer
                        .logoutUrl("/logout")
                        .logoutSuccessHandler((request, response, authentication) -> {
                                    response.sendRedirect("/api/oauth2/logout-success");
                                })
                        .deleteCookies("JSESSIONID")
                );

        return http.build();
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration=new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*"); //모든 출처에 대해 전부 허용
        corsConfiguration.addAllowedMethod("*"); //모든 메소드에 대해 전부 허용
        corsConfiguration.addAllowedHeader("*"); //모든 헤더에 대해 전부 허용

        UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

    @Bean
    public AuthenticationSuccessHandler successHandler(){
        return ((request, response, authentication) -> {
            DefaultOAuth2User defaultOAuth2User=(DefaultOAuth2User) authentication.getPrincipal();

            String clientId=defaultOAuth2User.getName();
            String img = defaultOAuth2User.getAttributes().get("img").toString();

            // 회원 정보 조회
            boolean isPresentUser= userService.isPresentUser(clientId);

            if(!isPresentUser) { //회원이 아닐 경우
                response.sendRedirect("/auth/oauth2-response/join?id="+clientId+"&img="+img);
            }
            else { //기존 회원일 경우
                String token = userService.createToken(clientId); //이거 값 바꿔야 됨
                response.sendRedirect("/auth/oauth2-response/login/"+token);
            }

//            //만약 redirect가 아니라 respone body를 사용하고 싶다면
//            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//            response.setCharacterEncoding(StandardCharsets.UTF_8.name());
//            response.getWriter().write("{\"code\": \"NP\", \"message\": \"No Permission\"}");

        });
    }

}


class FailedAuthenticationEntryPoint implements AuthenticationEntryPoint{

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("{\"code\": \"NP\", \"message\": \"No Permission\"}");
    }
}