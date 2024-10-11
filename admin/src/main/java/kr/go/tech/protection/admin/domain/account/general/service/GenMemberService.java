package kr.go.tech.protection.admin.domain.account.general.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import kr.go.tech.protection.admin.domain.account.general.dao.GenMemberDAO;
import kr.go.tech.protection.admin.domain.account.general.dto.GenMemberPO;
import kr.go.tech.protection.admin.domain.account.general.dto.GenMemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class GenMemberService {
    private final GenMemberDAO genMemberDAO;

    public GenMemberPO.ListResponsePO selectGenMemberList(GenMemberPO.SearchPO searchPO) {
        List<GenMemberVO.ListResponseVO> genMemberList = genMemberDAO.selectGenMemberList(searchPO);
        AtomicReference<Integer> rowNum = new AtomicReference<>(1);

        return GenMemberPO.ListResponsePO.builder()
                .totalCount(genMemberList.size())
                .list(
                    genMemberList.stream().map(genMember-> GenMemberPO.ListData.builder()
                                .no(rowNum.getAndSet(rowNum.get() + 1))
                                .companyName(genMember.getConmNm())
                                .genName(genMember.getMbrNm())
                                .genId(genMember.getMbrId())
                                .genPhone(genMember.getMbrMblTelno())
                                .genEmail(genMember.getEmlAddr())
                                .createdAt(genMember.getFrstRegDt())
                                .modifiedAt(genMember.getLastMdfcnDt())
                                .build()).collect(Collectors.toList())
                )
                .build();
    }

}
