package kr.go.tech.protection.admin.domain.member.service;

import kr.go.tech.protection.admin.domain.member.dao.MemberDAO;
import kr.go.tech.protection.admin.domain.member.dto.BaseMemberVO;
import kr.go.tech.protection.admin.domain.member.dto.MemberPO;
import kr.go.tech.protection.admin.domain.member.dto.MemberVO;
import kr.go.tech.protection.admin.global.exception.ErrorCode;
import kr.go.tech.protection.admin.global.exception.GlobalException;
import kr.go.tech.protection.admin.global.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
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
public class MemberService {
    private final MemberDAO adminDao;

    public MemberPO.ListResponsePO selectAdminMemberList(MemberPO.SearchPO searchPO) {
        List<MemberVO.ListResponseVO> adminList = adminDao.selectAdminMemberList(searchPO);
        AtomicReference<Integer> rowNum = new AtomicReference<>(1);

        return MemberPO.ListResponsePO.builder()
                .totalCount(adminList.size())
                .list(
                        adminList.stream().map(admin-> MemberPO.ListData.builder()
                                .no(rowNum.getAndSet(rowNum.get() + 1))
                                .adminNo(admin.getMngrNo())
                                .authGroupName(admin.getAuthrtNm())
                                .adminId(admin.getMngrId())
                                .name(admin.getMngrNm())
                                .phone(admin.getMngrMblTelno())
                                .email(admin.getMngrEml())
                                .regDt(DateUtil.formatLocalDateToString(admin.getFrstRegDt()))
                                .modDt(DateUtil.formatLocalDateToString(admin.getLastMdfcnDt()))
                                .build()).collect(Collectors.toList())
                )
                .build();
    }

    public MemberPO.DetailResponsePO selectAdminMemberByNo(int no) {
        MemberVO.DefaultMemberVO member = adminDao.selectAdminMemberByNo(no);

        if(ObjectUtils.isEmpty(member)) {
            throw new GlobalException(ErrorCode.USER_NOT_FOUND);
        }

        return MemberPO.DetailResponsePO.builder()
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

    public MemberPO.SearchIdResponsePO selectAdminMemberById(MemberPO.SearchIdRequestPO searchIdRequestPO) {
        if(searchIdRequestPO.getSearchId() == null) {
            throw new GlobalException(ErrorCode.NOT_FOUND_ADMIN);
        }

        MemberVO.DefaultMemberVO member = adminDao.selectAdminMemberById(searchIdRequestPO.getSearchId());

        return MemberPO.SearchIdResponsePO.builder()
                .idAvailable(member==null)
                .build();
    }

    @Transactional
    public void deleteAdminMember(int no) {
        MemberVO.DefaultMemberVO member = adminDao.selectAdminMemberByNo(no);

        if(member == null) {
            throw new GlobalException(ErrorCode.NOT_FOUND_ADMIN);
        }

        Integer result = adminDao.deleteAdminMember(no);
        if(result < 1) {
            throw new GlobalException(ErrorCode.ADMIN_DELETE_FAILED);
        }
    }

    @Transactional
    public MemberPO.RegResponsePO insertAdminMember(@Valid @RequestBody MemberPO.RegRequestPO responsePO) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        BaseMemberVO admin = (BaseMemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        MemberVO.DefaultMemberVO member = adminDao.selectAdminMemberById(responsePO.getAdminId());
        if(member != null){
            throw new GlobalException(ErrorCode.DUPLICATION_ADMIN);
        }

        MemberVO.RegRequestVO requestVO = MemberVO.RegRequestVO.builder()
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
            throw new GlobalException(ErrorCode.ADMIN_INSERT_FAILED);
        }

        MemberVO.DefaultMemberVO memberVO = adminDao.selectAdminMemberById(requestVO.getMngrId());
        if(memberVO==null) {
            throw new GlobalException(ErrorCode.NOT_FOUND_ADMIN);
        }

        return MemberPO.RegResponsePO
                .builder()
                .adminNo(memberVO.getMngrNo())
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
    public MemberPO.PasswordResponsePO updatePassword(MemberPO.PasswordRequestPO requestPO) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        MemberVO.DefaultMemberVO member = adminDao.selectAdminMemberById(requestPO.getAdminId());
        if (member == null) {
            throw new GlobalException(ErrorCode.NOT_FOUND_ADMIN);
        }

        if(!encoder.matches(requestPO.getTempPassword(),member.getMngrPswd())) {
            throw new GlobalException(ErrorCode.NOT_MATCHED_ADMIN_PASSWORD);
        }

        if(!requestPO.getPassword().equals(requestPO.getPasswordCheck())) {
            throw new GlobalException(ErrorCode.NOT_MATCHED_ADMIN_CHECK_PASSWORD);
        }

        BaseMemberVO admin = (BaseMemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        MemberVO.PasswordRequestVO param = MemberVO.PasswordRequestVO.builder()
                .mngrId(requestPO.getAdminId())
                .mngrPswd(encoder.encode(requestPO.getPassword()))
                .tmprPswdYn("N")
                .build();
        param.setLast(admin.getMngrId());

        int result = adminDao.updatePassword(param);
        if (result < 1) {
            throw new GlobalException(ErrorCode.ADMIN_UPDATE_FAILED);
        }

        return MemberPO.PasswordResponsePO.builder()
                .adminId(requestPO.getAdminId())
                .build();
    }

    @Transactional
    public MemberPO.ResetPasswordResponsePO resetPassword(MemberPO.ResetPasswordRequestPO requestPO) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        MemberVO.DefaultMemberVO member = adminDao.selectAdminMemberById(requestPO.getAdminId());
        if (member == null) {
            throw new GlobalException(ErrorCode.NOT_FOUND_ADMIN);
        }

        BaseMemberVO admin = (BaseMemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        MemberVO.ResetPasswordRequestVO param = MemberVO.ResetPasswordRequestVO.builder()
                .mngrId(requestPO.getAdminId())
                .mngrPswd(encoder.encode(getTempPassword()))
                .tmprPswdYn("Y")
                .build();
        param.setLast(admin.getMngrId());

        int result = adminDao.resetPassword(param);
        if(result < 1) {
            throw new GlobalException(ErrorCode.ADMIN_UPDATE_FAILED);
        }

        return MemberPO.ResetPasswordResponsePO.builder()
                .adminId(requestPO.getAdminId())
                .adminNo(member.getMngrNo())
                .build();
    }

    @Transactional
    public MemberPO.UpdateResponsePO updateAdminMember(MemberPO.UpdateRequestPO requestPO) {
        BaseMemberVO admin = (BaseMemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MemberVO.DefaultMemberVO member = adminDao.selectAdminMemberById(requestPO.getAdminId());
        if (member == null) {
            throw new GlobalException(ErrorCode.NOT_FOUND_ADMIN);
        }

        MemberVO.UpdateRequestVO param = MemberVO.UpdateRequestVO.builder()
                .mngrId(requestPO.getAdminId())
                .mngrNm(requestPO.getAdminName())
                .mngrTelno(requestPO.getTelNo())
                .mngrMblTelno(requestPO.getPhoneNo())
                .mngrEml(requestPO.getEmail())
                .authrtNo(requestPO.getAuthGroupNo())
                .build();
        param.setLast(admin.getMngrId());

        int result = adminDao.updateAdminMember(param);
        if(result < 1) {
            throw new GlobalException(ErrorCode.ADMIN_UPDATE_FAILED);
        }

        return MemberPO.UpdateResponsePO.builder()
                .adminNo(requestPO.getAdminNo())
                .adminId(requestPO.getAdminId())
                .adminName(requestPO.getAdminName())
                .telNo(requestPO.getTelNo())
                .phoneNo(requestPO.getPhoneNo())
                .email(requestPO.getEmail())
                .authGroupNo(requestPO.getAuthGroupNo())
                .authGroupName(requestPO.getAuthGroupName())
                .build();
    }
}
