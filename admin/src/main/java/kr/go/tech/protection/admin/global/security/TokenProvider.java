package kr.go.tech.protection.admin.global.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import kr.go.tech.protection.admin.domain.member.service.CustomUserDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenProvider {
    @Value("${jwt.secret}")
    private String SECRET_KEY;
    private long tokenValidTime;
    private final CustomUserDetailService customUserDetailService;

    @PostConstruct
    protected void init() {
        this.SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.getBytes());
        this.tokenValidTime = 30 * 60 * 1000L; // 30분
    }

    public String generateToken(String memberId, String authGroupNo) {
        Claims claims = Jwts.claims().setSubject(memberId);
        claims.put("authGroupNo", authGroupNo);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + tokenValidTime)) // 토큰 유효시각 설정
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)  // 암호화 알고리즘과, secret 값
                .compact();
    }

    // 인증 정보 조회
    public Authentication getAuthentication(String token) throws UsernameNotFoundException {
        UserDetails userDetails = customUserDetailService.loadUserByUsername(this.getUserPk(token));

//        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // TODO: 배포시 삭제
    public Authentication getDevAuthentication() throws UsernameNotFoundException {
        UserDetails userDetails = customUserDetailService.loadUserByUsername("superadmin");
        return new UsernamePasswordAuthenticationToken(userDetails, "1",userDetails.getAuthorities());
    }


    // 토큰에서 회원 정보 추출
    public String getUserPk(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody().getSubject();
    }

    public Jws<Claims> parseClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token);
    }

    // 토큰 유효시간 체크
    public boolean validateExpiration(Jws<Claims> claimsJws) {
        try {
            if(claimsJws.getBody().getExpiration().before(new Date())) {
                return false;
            }
            return true;
        }catch (Exception e) {
            return false;
        }
    }

//    public static String getLoginId(String token) {
//
//    }

    // Request의 Header에서 token 값 가져오기
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("X-AUTH-TOKEN");
    }
}
