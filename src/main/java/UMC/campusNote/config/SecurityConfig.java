package UMC.campusNote.config;

import UMC.campusNote.auth.jwt.JwtAuthenticationFilter;
import UMC.campusNote.common.exception.handler.CustomAccessDeniedHandler;
import UMC.campusNote.common.exception.handler.CustomAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import static UMC.campusNote.user.entity.Permission.*;
import static UMC.campusNote.user.entity.Role.MANAGER;
import static io.lettuce.core.AclCategory.ADMIN;
import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfig {
    private static final String URL = "/api/v1/management/**";
    private static final String[] WHITE_LIST_URL = {
            "/",
            "/api/v1/auth/**",
            "/v2/api-docs",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui/**",
            "/webjars/**",
            "/swagger-ui.html",
            "/h2-console/**"
            ,"/error/**"
    };
    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req ->
                        req.requestMatchers(WHITE_LIST_URL)
                                .permitAll()
                                .requestMatchers(URL).hasAnyRole(ADMIN.name(), MANAGER.name())
                                .requestMatchers(GET, URL).hasAnyAuthority(ADMIN_READ.name(), MANAGER_READ.name())
                                .requestMatchers(POST, URL).hasAnyAuthority(ADMIN_CREATE.name(), MANAGER_CREATE.name())
                                .requestMatchers(PUT, URL).hasAnyAuthority(ADMIN_UPDATE.name(), MANAGER_UPDATE.name())
                                .requestMatchers(DELETE, URL).hasAnyAuthority(ADMIN_DELETE.name(), MANAGER_DELETE.name())
                                .anyRequest()
                                .authenticated()
                )
                .headers().frameOptions().disable() //h2 콘솔 접근하기 위해 추가 나중에 지우면 됨
                .and()
                .sessionManagement(session -> session.sessionCreationPolicy(STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(authenticationProvider)
                .logout(logout ->
                        logout.logoutUrl("/api/v1/auth/logout")
                                .addLogoutHandler(logoutHandler)
                                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                );
        http.exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint);
        http.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);
        return http.build();

    }

}
