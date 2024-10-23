package kr.go.tech.protection.admin.domain.login.service;

import kr.go.tech.protection.admin.domain.login.dto.LoginPO;
import kr.go.tech.protection.admin.domain.member.dao.MemberDAO;
import kr.go.tech.protection.admin.domain.member.dto.MemberVO;
import kr.go.tech.protection.admin.global.exception.ErrorCode;
import kr.go.tech.protection.admin.global.exception.GlobalException;
import kr.go.tech.protection.admin.global.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {

    private final TokenProvider tokenProvider;
    private final MemberDAO adminDAO;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    public LoginPO.LoginResponsePO login(LoginPO.LoginRequestPO requestPO, HttpServletResponse response) {
        // 회원정보 가져오기
        MemberVO.DefaultMemberVO memberVO = authenticateLoginIdAndPassword(requestPO);

        Integer authGroupNo = memberVO.getAuthrtNo();

        // access token 발급
        String accessToken = tokenProvider.generateToken(memberVO.getMngrId(), String.valueOf(authGroupNo));

//        String newRefreshToken = UUID.randomUUID().toString();
//        RefreshToken refreshToken = RefreshToken.builder()
//                .refreshToken(newRefreshToken)
//                .loginId(memberVO.getMngrId())
//                .build();

        return LoginPO.LoginResponsePO.builder()
                .accessToken(accessToken)
                .authGroupNo(authGroupNo)
                .adminId(memberVO.getMngrId())
                .adminName(memberVO.getMngrNm())
                .authGroupName(memberVO.getAuthrtNm())
                .passwordExpiredYn(memberVO.getPswdMdfrDt().plusDays(90).isBefore(LocalDateTime.now()))
                .tempPasswordYn(memberVO.getTmprPswdYn().equals("Y"))
                .build();
    }

    public MemberVO.DefaultMemberVO authenticateLoginIdAndPassword(LoginPO.LoginRequestPO requestPO) {
        MemberVO.DefaultMemberVO memberVO = adminDAO.selectAdminMemberById(requestPO.getAdminId());
        if(memberVO == null) {
            throw new GlobalException(ErrorCode.NOT_FOUND_ADMIN);
        }
        
        boolean match = passwordEncoder.matches(requestPO.getPassword(), memberVO.getMngrPswd());
        if(!match) {
            throw new GlobalException(ErrorCode.NOT_MATCHED_ADMIN_PASSWORD);
        }
        return memberVO;
    }
}
