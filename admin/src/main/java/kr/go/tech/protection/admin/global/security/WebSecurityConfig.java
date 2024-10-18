package kr.go.tech.protection.admin.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final TokenProvider tokenProvider;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors(cors -> cors
                        .configurationSource(CorsConfig.corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .headers().frameOptions().sameOrigin().and()
                .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(
                    httpSecuritySessionManagementConfigurer -> {
                        try {
                            httpSecuritySessionManagementConfigurer.sessionCreationPolicy(
                                    SessionCreationPolicy.STATELESS)

                    .and()
                    .authorizeHttpRequests()
                        .antMatchers("/api/v1/login",
                                "/swagger-resources/**", "/swagger-ui/index.html"
                                , "/swagger-ui.html").permitAll()
                        .anyRequest().authenticated()
                            ;
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                    }
                );
        return http.build();
    }

//    protected  void configure(HttpSecurity http) throws Exception {
//        http
//            .csrf().disable().headers().frameOptions().disable()
//            .and()
//
//            //세션 사용 안함
//            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//            .and()
//
//            .authorizeHttpRequests() // HttpServletRequest를 사용하는 요청들에 대한 접근제한을 설정하겠다.
//            .antMatchers("/api/**","/api/v1/**",
//                    "/swagger-resources/**", "/swagger-ui/index.html"
//                    , "/swagger-ui.html").permitAll()
//            .anyRequest().authenticated()
//            .and()
//
//            .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class);
//    }
}
