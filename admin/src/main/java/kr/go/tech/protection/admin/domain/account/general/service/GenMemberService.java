package kr.go.tech.protection.admin.domain.account.general.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import kr.go.tech.protection.admin.domain.account.general.dao.GenMemberDAO;
import kr.go.tech.protection.admin.domain.account.general.dto.GenMemberPO;
import kr.go.tech.protection.admin.domain.account.general.dto.GenMemberPO.DetailResponsePO;
import kr.go.tech.protection.admin.domain.account.general.dto.GenMemberVO;
import kr.go.tech.protection.admin.domain.account.general.dto.GenMemberVO.DetailGenMemberVO;
import kr.go.tech.protection.admin.domain.member.dto.MemberPO;
import kr.go.tech.protection.admin.domain.member.dto.MemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public GenMemberPO.DetailResponsePO selectGenMemberByNo(int no) {

        //회원정보 상세 조회 및 소속기업 정보 조회
        GenMemberVO.DetailGenMemberVO genMember = genMemberDAO.selectGenMemberByNo(no);

             return GenMemberPO.DetailResponsePO.builder()
                     .genName(genMember.getMbrNm())
                     .gender(genMember.getMbrGndrCd())
                     .birthDate(genMember.getMbrBrdt())
                     .genId(genMember.getMbrId())
                     .genPhone(genMember.getMbrMblTelno())
                     .isEmailConsent(genMember.getEmlRcptnAgreYn())
                     .address(genMember.getAddress())
                     .companyName(genMember.getConmNm())
                     .businessRegistrationNumber(genMember.getBrNo())
                     .department(genMember.getDeptNm())
                     .position(genMember.getJbpsCd())
                     .companyAddress(genMember.getCompanyAddress())
                     .build();
    }

    @Transactional
    public void deleteGenMember(int no) {
        GenMemberVO.DetailGenMemberVO GenMember = genMemberDAO.selectGenMemberByNo(no);

        if(GenMember == null) {
            log.info("FAILED");
            // TODO null 처리
        }

        Integer result = genMemberDAO.deleteGenMember(no);

        if(result < 1) {
            log.info("FAILED");
            // TODO 삭제 실패 처리
        }

    }

}
