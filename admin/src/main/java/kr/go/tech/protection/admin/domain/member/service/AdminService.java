package kr.go.tech.protection.admin.domain.member.service;

import kr.go.tech.protection.admin.domain.member.dao.AdminDAO;
import kr.go.tech.protection.admin.domain.member.dto.AdminPO;
import kr.go.tech.protection.admin.domain.member.dto.AdminVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
                        .groupNm(admin.getDeptNm())
                        .id(admin.getMngrId())
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
                .managerNo(member.getMngrNo())
                .managerId(member.getMngrId())
                .managerName(member.getMngrNm())
                .telNo(member.getMngrTelno())
                .phoneNo(member.getMngrMblTelno())
                .email(member.getMngrEml())
                .deptName(member.getDeptNm())
                .deptNo(member.getDeptNo())
                .build();

    }
}
