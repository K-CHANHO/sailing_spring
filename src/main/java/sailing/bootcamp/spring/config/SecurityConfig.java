package sailing.bootcamp.spring.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.PathPatternRequestMatcher;
import sailing.bootcamp.spring.filter.JwtAuthenticationFilter;
import sailing.bootcamp.spring.user.entity.Role;
import sailing.bootcamp.spring.user.service.JwtService;
import sailing.bootcamp.spring.user.service.UserDetailsServiceImpl;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .sessionManagement(config -> config.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeRequest ->
                        authorizeRequest
                                .requestMatchers(
                                        PathPatternRequestMatcher.withDefaults().matcher("/h2-console/**")
                                ).permitAll()
                                .requestMatchers(
                                        PathPatternRequestMatcher.withDefaults().matcher("/api/v1/login")
                                ).permitAll()
                                .requestMatchers(
                                        PathPatternRequestMatcher.withDefaults().matcher("/api/v1/user")
                                ).permitAll()
                                .requestMatchers(
                                        PathPatternRequestMatcher.withDefaults().matcher("/error")
                                ).permitAll()
                                .requestMatchers(
                                        PathPatternRequestMatcher.withDefaults().matcher(HttpMethod.DELETE, "/api/v1/board")
                                ).hasRole(Role.ADMIN.name())
                                .anyRequest().hasRole(Role.USER.name())
                )
                .headers(headersConfigurer ->
                        headersConfigurer
                                .frameOptions(
                                        HeadersConfigurer.FrameOptionsConfig::sameOrigin
                                )
                )
                .addFilterBefore(new JwtAuthenticationFilter(userDetailsService, jwtService), UsernamePasswordAuthenticationFilter.class)
        ;

        return http.build();

    }

}
