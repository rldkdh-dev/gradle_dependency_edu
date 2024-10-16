package kr.go.tech.protection.admin.domain.account.general.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import kr.go.tech.protection.admin.domain.account.general.dao.GenMemberDAO;
import kr.go.tech.protection.admin.domain.account.general.dto.GenMemberPO;
import kr.go.tech.protection.admin.domain.account.general.dto.GenMemberVO;
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
public class GenMemberService {

	private final GenMemberDAO genMemberDAO;

	public GenMemberPO.ListResponsePO selectGenMemberList(GenMemberPO.SearchPO searchPO) {
		List<GenMemberVO.ListResponseVO> genMemberList = genMemberDAO.selectGenMemberList(searchPO);
		AtomicReference<Integer> rowNum = new AtomicReference<>(1);

		return GenMemberPO.ListResponsePO.builder()
			.totalCount(genMemberList.size())
			.list(
				genMemberList.stream().map(genMember -> GenMemberPO.ListData.builder()
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
			.genderCd(genMember.getMbrGndrCd())
			.birthDate(genMember.getMbrBrdt())
			.genId(genMember.getMbrId())
			.genPhone(genMember.getMbrMblTelno())
			.isEmailConsent(genMember.getEmlRcptnAgreYn())
			.address(genMember.getAddress())
			.companyName(genMember.getConmNm())
			.businessNumber(genMember.getBrNo())
			.department(genMember.getDeptNm())
			.position(genMember.getJbpsCd())
			.companyAddress(genMember.getCompanyAddress())
			.build();
	}


	@Transactional
	public GenMemberPO.UpdateResponsePO updateGenMember(GenMemberPO.UpdateRequestPO requestPO) {
		BaseMemberVO admin = (BaseMemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		GenMemberVO.DefaultGenMemberVO genMember = genMemberDAO.selectGenMemberById(requestPO.getGenId());
		if (genMember == null) {
			// TODO null 체크
		}

		// 수정하려는 사업자 등록번호 값 검증
		if (requestPO.getBusinessNumber() != null && !requestPO.getBusinessNumber().isEmpty()) {
			String entMemberNo = genMemberDAO.selectEntMemberNoByBusinessNumber(requestPO.getBusinessNumber());

			if (entMemberNo != null) {
				GenMemberVO.UpdateEntPrcptRequestVO updateEntPrcptRequestParam = UpdateEntPrcptRequestVO.builder()
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

		GenMemberVO.UpdateRequestVO param = GenMemberVO.UpdateRequestVO.builder()
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

		return GenMemberPO.UpdateResponsePO.builder()
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
		GenMemberVO.DetailGenMemberVO GenMember = genMemberDAO.selectGenMemberByNo(no);

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
	public GenMemberPO.ResetPasswordResponsePO resetPassword(
		GenMemberPO.ResetPasswordRequestPO requestPO) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		GenMemberVO.DefaultGenMemberVO genMember = genMemberDAO.selectGenMemberById(requestPO.getGenId());

		if (genMember == null) {
			// TODO null 체크
		}

		// 로그인 된 관리자 회원
		BaseMemberVO admin = (BaseMemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// 12자리 비밀번호 난수 생성
		String tempPassword = NumberUtil.generateTempPassword();

		GenMemberVO.ResetPasswordRequestVO param = GenMemberVO.ResetPasswordRequestVO.builder()
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

		return GenMemberPO.ResetPasswordResponsePO.builder()
			.genNo(genMember.getMbrNo())
			.genId(requestPO.getGenId())
			.build();
	}

}


