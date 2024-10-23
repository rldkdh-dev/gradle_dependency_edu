package kr.go.tech.protection.user.domain.signup.service;

import kr.go.tech.protection.user.domain.signup.dao.SignUpDAO;
import kr.go.tech.protection.user.domain.signup.dto.SignUpPO;
import kr.go.tech.protection.user.domain.signup.dto.SignUpVO;
import kr.go.tech.protection.user.domain.signup.dto.SignUpVO.InsertEntPrcptRequestVO;
import kr.go.tech.protection.user.global.exception.ErrorCode;
import kr.go.tech.protection.user.global.exception.GlobalException;
import kr.go.tech.protection.user.global.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class SignUpService {

	private final SignUpDAO signUpDAO;

	public SignUpPO.GenSignUpResponsePO SignUpGenMember(SignUpPO.GenSignUpRequestPO requestPO) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		SignUpVO.GenSignUpRequestVO requestVO = SignUpVO.GenSignUpRequestVO.builder()
			.mbrNm(requestPO.getGenName())
			.mbrGndrCd(requestPO.getGenderCd())
			.mbrBrdt(DateUtil.convertStringToTimestamp(requestPO.getBirthDate()))
			.mbrId(requestPO.getGenId())
			.mbrPswd(encoder.encode(requestPO.getPassword()))
			.mbrMblTelno(requestPO.getGenPhone())
			.emlAddr(requestPO.getGenEmail())
			.emlRcptnAgreYn(requestPO.getEmailConsentYn())
			.homeZip(requestPO.getZipCode())
			.homeRoadNm(requestPO.getRoadName())
			.homeDaddr(requestPO.getDetailAddress())
			.build();

		requestVO.setFirst(requestPO.getGenId());

		int result = signUpDAO.insertGenMember(requestVO);

		if (result > 0) {
			// 사업자등록번호 유효시 기업회원 소속 등록  - 사업자 등록번호 입력 후 가입시 기업소속회원 테이블에 해당 회원 번호로 인서트 허용여부 컬럼은 N 삭제여부 컬럼 N으로 인서트
			if (requestPO.getBusinessNumber() != null && !requestPO.getBusinessNumber().isEmpty()) {
				SignUpVO.DefaultEntMemberVO entMember = signUpDAO.selectEntMemberByBusinessNumber(requestPO.getBusinessNumber());

				if (!ObjectUtils.isEmpty(entMember)) {
					SignUpVO.InsertEntPrcptRequestVO insertEntPrcptRequestParam = InsertEntPrcptRequestVO.builder()
						.entMbrNo(entMember.getEntMbrNo())
						.mbrNo(requestVO.getMbrNo())
						//TODO : 회원가입할때 해당 회원의 사내에서의 구분코드, 직위코드, 부서명은 안받는데 어떻게 처리해야할지?
						.seCd(null)
						.jbpsCd(null)
						.deptNm(null)
						.build();
					insertEntPrcptRequestParam.setFirst(requestPO.getGenId());

					if (signUpDAO.insertEntPrcpt(insertEntPrcptRequestParam) < 1) {
						throw new GlobalException(ErrorCode.ENT_PRCPT_REGISTRATION_FAILED);
					}
				}
			}

		}else{
			throw new GlobalException(ErrorCode.SIGN_UP_FAILED);
		}

		return SignUpPO.GenSignUpResponsePO.builder()
			.genNo(requestVO.getMbrNo())
			.genName(requestPO.getGenName())
			.genderCd(requestPO.getGenderCd())
			.birthDate(requestPO.getBirthDate())
			.genId(requestPO.getGenId())
			.genPhone(requestPO.getGenPhone())
			.genEmail(requestPO.getGenEmail())
			.emailConsentYn(requestPO.getEmailConsentYn())
			.zipCode(requestPO.getZipCode())
			.roadName(requestPO.getRoadName())
			.detailAddress(requestPO.getDetailAddress())
			.build();
	}

	public Boolean checkSignupStatusByPassAuth(SignUpPO.PassAuthRequestPO requestPO) {
		return signUpDAO.selectGenMemberByPassAuth(requestPO) < 1;

	}

	public boolean checkGenMemberIdDuplicate(String searchId) {
		return signUpDAO.selectGenIdDuplicate(searchId) > 0;
	}

	public SignUpPO.EntSignUpResponsePO SignUpEntMember(SignUpPO.EntSignUpRequestPO requestPO) {
		SignUpVO.EntSignUpRequestVO requestVO = SignUpVO.EntSignUpRequestVO.builder()
			.conmNm(requestPO.getCompanyName())
			.rprsvNm(requestPO.getRepresentativeName())
			.brNo(requestPO.getBusinessNumber())
			.instSttsCd(requestPO.getInstitutionStatusCode())
			.bzmnTypeCd(requestPO.getBusinessTypeCode())
			.instTypeCd(requestPO.getInstitutionTypeCode())
			.bzentFndnDt(DateUtil.convertStringToTimestamp(requestPO.getEnterpriseFoundationDate()))
			.rprsBzstatCd(requestPO.getRepresentativeBusinessCode())
			.rprsTpbizCd(requestPO.getRepresentativeIndustryCode())
			.empCnt(requestPO.getEmployeeCount())
			.fctYn(requestPO.getFactoryYn())
			.prvCorpSeCd(requestPO.getIndividualCorporateSeparationCode())
			.prvCorpNo(requestPO.getIndividualCorporateNumber())
			.telNo(requestPO.getEntTelNo())
			.bplcZip("12345")	//TODO : 주소가 통째로 값이 들어오면 우편번호,도로명, 상세주소 분할해서 넣어야함
			.bplcRoadNm("도로명")
			.bplcDaddr("상세주소")
			.coHmpgAddr(requestPO.getHomepageUrl())
			.mainPrdctn(requestPO.getMainProduct())
			.picNm(requestPO.getManagerName())
			.picGndrCd(requestPO.getManagerGenderCode())
			.picBrdt(DateUtil.convertStringToTimestamp(requestPO.getManagerBirthDate()))
			.picMblTelno(requestPO.getManagerTelNo())
			.picSeCd(requestPO.getManagerTypeCode())
			.picDeptNm(requestPO.getManagerDeptName())
			.picJbpsCd(requestPO.getManagerPositionCode())
			.emlAddr(requestPO.getManagerEmail())
			.emlRcptnAgreYn(requestPO.getEmailConsentYn())
			.build();

		requestVO.setFirst(requestPO.getCompanyName());

		int result = signUpDAO.insertEntMember(requestVO);
		if(result < 1) {
			throw new GlobalException(ErrorCode.SIGN_UP_FAILED);
		}

		// 회원가입 후 제대로 등록 됐는지 사업자등록번호로 회원 여부 조회 후 프론트로 응답
		SignUpVO.DefaultEntMemberVO entMember = signUpDAO.selectEntMemberByBusinessNumber(requestVO.getBrNo());

		if(ObjectUtils.isEmpty(entMember)) {
			throw new GlobalException(ErrorCode.USER_NOT_FOUND);
		}

		return SignUpPO.EntSignUpResponsePO.builder()
			.entMbrNo(entMember.getEntMbrNo())
			.companyName(entMember.getConmNm())
			.representativeName(entMember.getRprsvNm())
			.businessNumber(entMember.getBrNo())
			.institutionStatusCode(entMember.getInstSttsCd())
			.businessTypeCode(entMember.getBzmnTypeCd())
			.institutionTypeCode(entMember.getInstTypeCd())
			.enterpriseFoundationDate(requestPO.getEnterpriseFoundationDate())
			.representativeBusinessCode(entMember.getRprsBzstatCd())
			.representativeIndustryCode(entMember.getRprsTpbizCd())
			.employeeCount(entMember.getEmpCnt())
			.factoryYn(entMember.getFctYn())
			.IndividualCorporateSeparationCode(entMember.getPrvCorpSeCd())
			.IndividualCorporateNumber(entMember.getPrvCorpNo())
			.entTelNo(entMember.getTelNo())
			.companyAddress(entMember.getBplcAddress())
			.homepageUrl(entMember.getCoHmpgAddr())
			.mainProduct(entMember.getMainPrdctn())
			.managerName(entMember.getPicNm())
			.managerGenderCode(entMember.getPicGndrCd())
			.managerBirthDate(requestPO.getManagerBirthDate())
			.managerTelNo(entMember.getPicMblTelno())
			.managerTypeCode(entMember.getPicSeCd())
			.managerDeptName(entMember.getPicDeptNm())
			.managerPositionCode(entMember.getPicJbpsCd())
			.managerEmail(entMember.getEmlAddr())
			.emailConsentYn(entMember.getEmlRcptnAgreYn())
			.build();
	}


	public Boolean checkSignupStatusByJointCertInfo(SignUpPO.JointCertInfoRequestPO requestPO) {
		return signUpDAO.selectEntMemberJointCertInfo(requestPO) < 1;
	}

	public boolean isBusinessNumberEntMemberRegistered(String businessNumber) {
		SignUpVO.DefaultEntMemberVO entMember = signUpDAO.selectEntMemberByBusinessNumber(businessNumber);

		return !ObjectUtils.isEmpty(entMember);
	}

}


