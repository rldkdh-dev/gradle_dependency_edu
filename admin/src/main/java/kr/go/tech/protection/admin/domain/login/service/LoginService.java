package kr.go.tech.protection.admin.domain.login.service;

import kr.go.tech.protection.admin.domain.login.dao.RefreshTokenDAO;
import kr.go.tech.protection.admin.domain.login.dto.LoginPO;
import kr.go.tech.protection.admin.domain.login.dto.RefreshToken;
import kr.go.tech.protection.admin.domain.member.dao.AdminDAO;
import kr.go.tech.protection.admin.domain.member.dto.AdminVO;
import kr.go.tech.protection.admin.global.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {

    private final TokenProvider tokenProvider;
    private final AdminDAO adminDAO;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RefreshTokenDAO refreshTokenDAO;

    @Transactional
    public LoginPO.LoginResponsePO login(LoginPO.LoginRequestPO requestPO, HttpServletResponse response) {
        // 회원정보 가져오기
        AdminVO.MemberVO memberVO = authenticateLoginIdAndPassword(requestPO);

        // TODO authNO 추가
        String authGroupNo = "1";

        // access token 발급
        String accessToken = tokenProvider.generateToken(memberVO.getMngrId(), String.valueOf(authGroupNo));

        // token이 있는 경우 삭제 후 재 발급
//        refreshTokenDAO.findByLoginId(memberVO.getMngrId()).ifPresent(refreshTokenDAO::delete);

        String newRefreshToken = UUID.randomUUID().toString();
        RefreshToken refreshToken = RefreshToken.builder()
                .refreshToken(newRefreshToken)
                .loginId(memberVO.getMngrId())
                .build();

        // refresh token 저장
//        refreshTokenDAO.save(refreshToken);

        return LoginPO.LoginResponsePO.builder()
                .accessToken(accessToken)
                .authGroupNo(authGroupNo)
                .adminId(memberVO.getMngrId())
                .adminName(memberVO.getMngrNm())
                .authGroupName(memberVO.getAuthrtNm())
                .build();
    }

    public AdminVO.MemberVO authenticateLoginIdAndPassword(LoginPO.LoginRequestPO requestPO) {
        AdminVO.MemberVO memberVO = adminDAO.selectAdminMemberById(requestPO.getAdminId());
        if(memberVO == null) {
            // TODO null 처리
        }
        
        boolean match = passwordEncoder.matches(requestPO.getPassword(), memberVO.getMngrPswd());
        if(!match) {
            log.info("PASSWORD NOT MATCHED!!!");
            // TODO 비밀번호 불일치 처리
        }
        return memberVO;
    }
}
