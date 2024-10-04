package kr.go.tech.protection.admin.domain.member.service;

import kr.go.tech.protection.admin.domain.member.dao.AdminDAO;
import kr.go.tech.protection.admin.domain.member.dto.AdminPO;
import kr.go.tech.protection.admin.domain.member.dto.AdminVO;
import kr.go.tech.protection.admin.domain.member.dto.BaseAdminVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.security.SecureRandom;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminDAO adminDao;

    public AdminPO.ListResponsePO selectAdminMemberList(AdminPO.SearchPO searchPO) {
        List<AdminVO.ListResponseVO> adminList = adminDao.selectAdminMemberList(searchPO);
        AtomicReference<Integer> rowNum = new AtomicReference<>(1);

        return AdminPO.ListResponsePO.builder()
                .totalCount(adminList.size())
                .list(
                    adminList.stream().map(admin-> AdminPO.ListData.builder()
                        .no(rowNum.getAndSet(rowNum.get() + 1))
                        .adminNo(admin.getMngrNo())
                        .authGroupName(admin.getAuthrtNm())
                        .adminId(admin.getMngrId())
                        .name(admin.getMngrNm())
                        .phone(admin.getMngrMblTelno())
                        .email(admin.getMngrEml())
                        .regDt(admin.getFrstRegDt())
                        .modDt(admin.getLastMdfcnDt())
                        .build()).collect(Collectors.toList())
                )
                .build();
    }

    public AdminPO.DetailResponsePO selectAdminMemberByNo(int no) {
        AdminVO.MemberVO member = adminDao.selectAdminMemberByNo(no);

        if(member == null) {
            // TODO null 처리
            log.info("FAILED");
        }

        return AdminPO.DetailResponsePO.builder()
                .adminNo(member.getMngrNo())
                .adminId(member.getMngrId())
                .adminName(member.getMngrNm())
                .telNo(member.getMngrTelno())
                .phoneNo(member.getMngrMblTelno())
                .email(member.getMngrEml())
                .authGroupName(member.getAuthrtNm())
                .authGroupNo(member.getAuthrtNo())
                .build();
    }

    public AdminPO.SearchIdResponsePO selectAdminMemberById(AdminPO.SearchIdRequestPO searchIdRequestPO) {
        AdminVO.MemberVO member = adminDao.selectAdminMemberById(searchIdRequestPO.getSearchId());

        return AdminPO.SearchIdResponsePO.builder()
                .idAvailable(member==null)
                .build();
    }

    @Transactional
    public void deleteAdminMember(int no) {
        AdminVO.MemberVO member = adminDao.selectAdminMemberByNo(no);

        if(member == null) {
            log.info("FAILED");
            // TODO null 처리
        }

        Integer result = adminDao.deleteAdminMember(no);
        if(result < 1) {
            log.info("FAILED");
            // TODO 삭제 실패 처리
        }
    }

    @Transactional
    public AdminPO.RegResponsePO insertAdminMember(@Valid @RequestBody AdminPO.RegRequestPO responsePO) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        BaseAdminVO admin = (BaseAdminVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        AdminVO.MemberVO member = adminDao.selectAdminMemberById(responsePO.getAdminId());
        if(member != null){
            // TODO 중복 확인 체크
        }

        AdminVO.RegRequestVO requestVO = AdminVO.RegRequestVO.builder()
                .authrtNo(responsePO.getAuthGroupNo())
                .mngrNm(responsePO.getAdminName())
                .mngrId(responsePO.getAdminId())
                .mngrPswd(encoder.encode(getTempPassword()))
                .tmprPswdYn("Y")
                .mngrTelNo(responsePO.getTelNo())
                .mngrMblTelNo(responsePO.getPhoneNo())
                .mngrEml(responsePO.getEmail())
                .useYn("Y")
                .build();
        requestVO.setFirst(admin.getMngrId());
        requestVO.setLast(admin.getMngrId());

        int result = adminDao.insertAdminMember(requestVO);
        if(result < 1) {
            // TODO insert 실패 처리
            log.info("FAIL");
        }

        AdminVO.MemberVO memberVO = adminDao.selectAdminMemberById(requestVO.getMngrId());
        if(memberVO==null) {
            // TODO null 처리
            log.info("FAIL");
        }

        return AdminPO.RegResponsePO
                .builder()
                .no(memberVO.getMngrNo())
                .adminId(memberVO.getMngrId())
                .adminName(memberVO.getMngrNm())
                .telNo(memberVO.getMngrTelno())
                .phoneNo(memberVO.getMngrMblTelno())
                .email(memberVO.getMngrEml())
                .authGroupNo(memberVO.getAuthrtNo())
                .authGroupNm(memberVO.getAuthrtNm())
                .build();
    }

    // 랜덤 문자열 10자리의 임시 비밀번호 발급
    public String getTempPassword() {

        char[] charSet = new char[] {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z',
                'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
                '!', '@', '#', '$', '%', '^', '&'
        };

        StringBuffer sb = new StringBuffer();
        SecureRandom sr = new SecureRandom();
        sr.setSeed(new Date().getTime());

        int len = charSet.length;
        for(int i=0; i<10; i++) {
            int idx = sr.nextInt(len);
            sb.append(charSet[idx]);
        }

        log.info("NEW PASSWORD : " + sb.toString());

        return sb.toString();
    }

    @Transactional
    public AdminPO.PasswordResponsePO updatePassword(AdminPO.PasswordRequestPO requestPO) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        AdminVO.MemberVO member = adminDao.selectAdminMemberById(requestPO.getId());
        if (member == null) {
            // TODO null 체크
        }

        if(!encoder.matches(requestPO.getTempPassword(),member.getMngrPswd())) {
            // TODO 임시 비밀번호 일치여부 확인
            log.info("NOT MATCH!!!!!");
        }

        if(!requestPO.getPassword().equals(requestPO.getPasswordCheck())) {
            // TODO 입력한 비밀번호, 비밀번호 확인 일치여부 확인
        }

        BaseAdminVO admin = (BaseAdminVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        AdminVO.PasswordRequestVO param = AdminVO.PasswordRequestVO.builder()
                .mngrId(requestPO.getId())
                .mngrPswd(encoder.encode(requestPO.getPassword()))
                .tmprPswdYn("N")
                .build();
        param.setLast(admin.getMngrId());

        int result = adminDao.updatePassword(param);
        if (result < 1) {
            // TODO update 에러 처리
        }

        return AdminPO.PasswordResponsePO.builder()
                .adminId(requestPO.getId())
                .build();
    }

    @Transactional
    public AdminPO.ResetPasswordResponsePO resetPassword(AdminPO.ResetPasswordRequestPO requestPO) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        AdminVO.MemberVO member = adminDao.selectAdminMemberById(requestPO.getAdminId());
        if (member == null) {
            // TODO null 체크
        }

        BaseAdminVO admin = (BaseAdminVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        AdminVO.ResetPasswordRequestVO param = AdminVO.ResetPasswordRequestVO.builder()
                .mngrId(requestPO.getAdminId())
                .mngrPswd(encoder.encode(getTempPassword()))
                .tmprPswdYn("Y")
                .build();
        param.setLast(admin.getMngrId());
        
        int result = adminDao.resetPassword(param);
        if(result < 1) {
            // TODO UPDATE 에러 처리
        }

        return AdminPO.ResetPasswordResponsePO.builder()
                .adminId(requestPO.getAdminId())
                .adminNo(member.getMngrNo())
                .build();
    }

}
