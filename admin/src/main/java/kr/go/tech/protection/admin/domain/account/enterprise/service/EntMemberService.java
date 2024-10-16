package kr.go.tech.protection.admin.domain.account.enterprise.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import kr.go.tech.protection.admin.domain.account.enterprise.dao.EntMemberDAO;
import kr.go.tech.protection.admin.domain.account.enterprise.dto.EntMemberPO;
import kr.go.tech.protection.admin.domain.account.enterprise.dto.EntMemberVO;
import kr.go.tech.protection.admin.domain.account.general.dto.GenMemberVO.UpdateEntPrcptRequestVO;
import kr.go.tech.protection.admin.domain.member.dto.BaseMemberVO;
import kr.go.tech.protection.admin.global.util.NumberUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EntMemberService {

	private final EntMemberDAO entMemberDAO;

	public EntMemberPO.ListResponsePO selectEntMemberList(EntMemberPO.SearchPO searchPO) {
		List<EntMemberVO.ListResponseVO> entMemberList = entMemberDAO.selectEntMemberList(searchPO);
		AtomicReference<Integer> rowNum = new AtomicReference<>(1);

		return EntMemberPO.ListResponsePO.builder()
			.totalCount(entMemberList.size())
			.list(
				entMemberList.stream().map(entMember -> EntMemberPO.ListData.builder()
					.no(rowNum.getAndSet(rowNum.get() + 1))
					.entNo(entMember.getEntMbrNo())
					.companyName(entMember.getConmNm())
					.businessNumber(entMember.getBrNo())
					.representativeName(entMember.getRprsvNm())
					.managerName(entMember.getPicNm())
					.managerTelno(entMember.getPicMblTelno())
					.managerEmail(entMember.getEmlAddr())
					.createdAt(entMember.getFrstRegDt())
					.build()).collect(Collectors.toList())
			)
			.build();
	}




/*
	public EntMemberPO.DetailResponsePO selectGenMemberByNo(int no) {
		//회원정보 상세 조회 및 소속기업 정보 조회
		EntMemberVO.DetailGenMemberVO genMember = genMemberDAO.selectGenMemberByNo(no);

		return EntMemberPO.DetailResponsePO.builder()
			//회원정보
			.genName(genMember.getMbrNm())
			.genderCd(genMember.getMbrGndrCd())
			.birthDate(genMember.getMbrBrdt())
			.genId(genMember.getMbrId())
			.genPhone(genMember.getMbrMblTelno())
			.isEmailConsent(genMember.getEmlRcptnAgreYn())
			.zipCode(genMember.getHomeZip())
			.roadName(genMember.getHomeRoadNm())
			.detailAddress(genMember.getHomeDaddr())
			.address(genMember.getAddress())
			//소속기업정보
			.companyName(genMember.getConmNm())
			.businessNumber(genMember.getBrNo())
			.department(genMember.getDeptNm())
			.position(genMember.getJbpsCd())
			.companyZipcode(genMember.getBplcZip())
			.companyRoadName(genMember.getBlpcRoadNm())
			.companyDetailAddress(genMember.getBplcDaddr())
			.companyAddress(genMember.getCompanyAddress())
			//기업소속 승인여부
			.isAllow(genMember.getAlwYn())
			.build();
	}


	@Transactional
	public EntMemberPO.UpdateResponsePO updateGenMember(EntMemberPO.UpdateRequestPO requestPO) {
		BaseMemberVO admin = (BaseMemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		EntMemberVO.DefaultGenMemberVO genMember = genMemberDAO.selectGenMemberById(requestPO.getGenId());
		if (genMember == null) {
			// TODO null 체크
		}

		// 수정하려는 사업자 등록번호 값 검증
		if (requestPO.getBusinessNumber() != null && !requestPO.getBusinessNumber().isEmpty()) {
			String entMemberNo = genMemberDAO.selectEntMemberNoByBusinessNumber(requestPO.getBusinessNumber());

			if (entMemberNo != null) {
				UpdateEntPrcptRequestVO updateEntPrcptRequestParam = UpdateEntPrcptRequestVO.builder()
					.entMbrNo(entMemberNo)
					.mbrNo(requestPO.getGenNo())
					.build();

				updateEntPrcptRequestParam.setLast(admin.getMngrId());

				int result = genMemberDAO.updateEntPrcpt(updateEntPrcptRequestParam);
				log.info("회원 소속기업 수정 여부 = {}", result);

			} else {
				// TODO 회원정보 업데이트 하지않고 유효하지않은 사업자 번호라고 프론트쪽에 응답 처리
			}

		}

		EntMemberVO.UpdateRequestVO param = EntMemberVO.UpdateRequestVO.builder()
			.mbrNo(requestPO.getGenNo())
			.mbrNm(requestPO.getGenName())
			.mbrGndrCd(requestPO.getGenderCd())
			.mbrBrdt(requestPO.getBirthDate())
			.mbrId(requestPO.getGenId())
			.mbrMblTelno(requestPO.getGenPhone())
			.emlAddr(requestPO.getGenEmail())
			.emlRcptnAgreYn(requestPO.getIsEmailConsent())
			.homeZip(requestPO.getZipCode())
			.homeRoadNm(requestPO.getRoadName())
			.homeDaddr(requestPO.getDetailAddress())
			.build();
		param.setLast(admin.getMngrId());

		int result = genMemberDAO.updateGenMember(param);

		if (result < 1) {
			// TODO 업데이트 에러 처리
		}

		return EntMemberPO.UpdateResponsePO.builder()
			.genNo(requestPO.getGenNo())
			.genName(requestPO.getGenName())
			.genderCd(requestPO.getGenderCd())
			.birthDate(requestPO.getBirthDate())
			.genId(requestPO.getGenId())
			.genPhone(requestPO.getGenPhone())
			.genEmail(requestPO.getGenEmail())
			.isEmailConsent(requestPO.getIsEmailConsent())
			.zipCode(requestPO.getZipCode())
			.roadName(requestPO.getRoadName())
			.detailAddress(requestPO.getDetailAddress())
			.build();
	}

	@Transactional
	public void deleteGenMember(int no) {
		EntMemberVO.DetailGenMemberVO GenMember = genMemberDAO.selectGenMemberByNo(no);

		if (GenMember == null) {
			log.info("FAILED");
			// TODO null 처리
		}

		// TODO 진행중인 사업이 없을 경우 삭제 처리

		//진행중인 사업이 없을 경우 삭제
		int result = genMemberDAO.deleteGenMember(no);

		if (result > 0) {
			//기업 소속 회원 정보 테이블에서 삭제 여부 'Y' update
			genMemberDAO.updateEntPrcptMbrInfoDelYn(no);
		} else {

		}

	}

	@Transactional
	public EntMemberPO.ResetPasswordResponsePO resetPassword(
		EntMemberPO.ResetPasswordRequestPO requestPO) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		EntMemberVO.DefaultGenMemberVO genMember = genMemberDAO.selectGenMemberById(requestPO.getGenId());

		if (genMember == null) {
			// TODO null 체크
		}

		// 로그인 된 관리자 회원
		BaseMemberVO admin = (BaseMemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// 12자리 비밀번호 난수 생성
		String tempPassword = NumberUtil.generateTempPassword();

		EntMemberVO.ResetPasswordRequestVO param = EntMemberVO.ResetPasswordRequestVO.builder()
			.mbrId(requestPO.getGenId())
			.mbrPswd(encoder.encode(tempPassword))
			.build();

		// 수정시간 & 수정자 셋팅
		param.setLast(admin.getMngrId());

		int result = genMemberDAO.resetPassword(param);

		if (result > 0) {
			// TODO 휴대폰으로 임시 비밀번호 발송 로직 처리

			// TODO 이메일 수신 동의 시 이메일도 발송 처리
			if ("Y".equals(genMember.getEmlRcptnAgreYn())) {

			}

			// TODO 로그 등록

		} else {
			log.info("FAILED");
			// TODO 비밀번호 초기화 실패 처리
		}

		return EntMemberPO.ResetPasswordResponsePO.builder()
			.genNo(genMember.getMbrNo())
			.genId(requestPO.getGenId())
			.build();
	}*/

}

