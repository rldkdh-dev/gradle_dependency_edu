package kr.go.tech.protection.admin.global.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader(AUTHORIZATION_HEADER);

        // token 유효성 검증
        if(token != null && token.equals("dev")) {
            // 토큰으로부터 유저 정보를 받아
            Authentication authentication = tokenProvider.getDevAuthentication();
            // SecurityContext 에 객체 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 토큰 재발급
            String newToken = tokenProvider.generateToken("superadmin","1");
            response.setHeader(AUTHORIZATION_HEADER, newToken);
        }else if(token != null) {
            Jws<Claims> claims = tokenProvider.parseClaims(token);

            // 유효시간 검증
            if(tokenProvider.validateExpiration(claims)) {
                Authentication authentication = tokenProvider.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
                // 토큰 재발급
                String newToken = tokenProvider.generateToken(claims.getBody().getSubject(), claims.getBody().get("authGroupNo").toString());
                response.setHeader(AUTHORIZATION_HEADER, newToken);
            }
        }

        filterChain.doFilter(request,response);
    }
}
