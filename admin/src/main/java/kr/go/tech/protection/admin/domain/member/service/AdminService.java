package kr.go.tech.protection.admin.domain.member.service;

import kr.go.tech.protection.admin.domain.member.dao.AdminDAO;
import kr.go.tech.protection.admin.domain.member.dto.AdminVO;
import kr.go.tech.protection.admin.domain.member.dto.AdminPO;
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

    public AdminPO.ListResponsePO selectAdminMemberList(AdminPO.ResearchPO researchPO) {
        List<AdminVO.ListResponseVO> adminList = adminDao.selectAdminMemberList(researchPO);
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
}
