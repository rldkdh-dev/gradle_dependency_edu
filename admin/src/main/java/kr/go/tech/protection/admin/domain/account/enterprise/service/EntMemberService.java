package kr.go.tech.protection.admin.domain.account.enterprise.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import kr.go.tech.protection.admin.domain.account.enterprise.dao.EntMemberDAO;
import kr.go.tech.protection.admin.domain.account.enterprise.dto.EntMemberPO;
import kr.go.tech.protection.admin.domain.account.enterprise.dto.EntMemberVO;
import kr.go.tech.protection.admin.domain.member.dto.BaseMemberVO;
import kr.go.tech.protection.admin.global.exception.ErrorCode;
import kr.go.tech.protection.admin.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

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

	@Transactional
	public EntMemberPO.UpdateResponsePO updateGenMember(EntMemberPO.UpdateRequestPO requestPO) {
		BaseMemberVO admin = (BaseMemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		EntMemberVO.DefaultEntMemberVO entMember = entMemberDAO.selectEntMemberByNo(requestPO.getEntNo());

		if (ObjectUtils.isEmpty(entMember)) {
			throw new GlobalException(ErrorCode.USER_NOT_FOUND);
		}
		
		// TODO 사업장 주소 (전체 주소로 들어옴) 우편번호, 도로명, 상세주소로 나눠서 저장하는 로직 추가해야함

		EntMemberVO.UpdateRequestVO param = EntMemberVO.UpdateRequestVO.builder()
			.entMbrNo(requestPO.getEntNo())
			.conmNm(requestPO.getCompanyName())
			.rprsvNm(requestPO.getRepresentativeName())
			.brNo(requestPO.getBusinessNumber())
			.bzmnTypeCd(requestPO.getBusinessTypeCode())
			.instTypeCd(requestPO.getInstitutionTypeCode())
			.rprsBzstatCd(requestPO.getRepresentativeBusinessCode())
			.rprsTpbizCd(requestPO.getRepresentativeIndustryCode())
			.empCnt(requestPO.getEmployeeCount())
			.telNo(requestPO.getEntTelNo())
			.bplcZip("사업장 우편번호")
			.bplcRoadNm("사업장 도로명")
			.bplcDaddr("사업장 상세주소")
			.coHmpgAddr(requestPO.getHomepageUrl())
			.mainPrdctn(requestPO.getMainProduct())
			.fctYn(requestPO.getIsFactory())
			.picNm(requestPO.getManagerName())
			.picSeCd(requestPO.getManagerTypeCode())
			.picDeptNm(requestPO.getManagerDeptName())
			.picJbpsCd(requestPO.getManagerPositionCode())
			.picMblTelno(requestPO.getMangerTelno())
			.emlAddr(requestPO.getManagerEmail())
			.emlRcptnAgreYn(requestPO.getIsEmailConsent())
			.build();

		param.setLast(admin.getMngrId());

		int result = entMemberDAO.updateEntMember(param);

		if (result < 1) {
			throw new GlobalException(ErrorCode.NO_UPDATE);
		}

		return EntMemberPO.UpdateResponsePO.builder()
			.entNo(requestPO.getEntNo())
			.companyName(requestPO.getCompanyName())
			.representativeName(requestPO.getRepresentativeName())
			.businessNumber(requestPO.getBusinessNumber())
			.businessTypeCode(requestPO.getBusinessTypeCode())
			.institutionTypeCode(requestPO.getInstitutionTypeCode())
			.representativeBusinessCode(requestPO.getRepresentativeBusinessCode())
			.representativeIndustryCode(requestPO.getRepresentativeIndustryCode())
			.employeeCount(requestPO.getEmployeeCount())
			.entTelNo(requestPO.getEntTelNo())
			.companyAddress("우편번호 + 도로명 + 상세주소")
			.homepageUrl(requestPO.getHomepageUrl())
			.mainProduct(requestPO.getMainProduct())
			.isFactory(requestPO.getIsFactory())
			.managerName(requestPO.getManagerName())
			.managerTypeCode(requestPO.getManagerTypeCode())
			.managerDeptName(requestPO.getManagerDeptName())
			.managerPositionCode(requestPO.getManagerPositionCode())
			.managerTelNo(requestPO.getMangerTelno())
			.managerEmail(requestPO.getManagerEmail())
			.isEmailConsent(requestPO.getIsEmailConsent())
			.build();
	}

	// 사업자 등록번호 중복체크
	public boolean checkBusinessNumber(String businessNumber) {
		return entMemberDAO.isBusinessNumberDuplicate(businessNumber) > 0;
	}


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



