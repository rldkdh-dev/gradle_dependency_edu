package kr.go.tech.protection.admin.domain.account.enterprise.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import javax.validation.Valid;
import kr.go.tech.protection.admin.domain.account.enterprise.dao.EntMemberDAO;
import kr.go.tech.protection.admin.domain.account.enterprise.dto.EntMemberPO;
import kr.go.tech.protection.admin.domain.account.enterprise.dto.EntMemberPO.DetailResponsePO;
import kr.go.tech.protection.admin.domain.account.enterprise.dto.EntMemberVO;
import kr.go.tech.protection.admin.domain.member.dto.BaseMemberVO;
import kr.go.tech.protection.admin.global.exception.ErrorCode;
import kr.go.tech.protection.admin.global.exception.GlobalException;
import kr.go.tech.protection.admin.global.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;

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
					.createdAt(DateUtil.formatLocalDateToString(entMember.getFrstRegDt()))
					.build()).collect(Collectors.toList())
			)
			.build();
	}

	@Transactional
	public EntMemberPO.UpdateResponsePO updateEntMember(EntMemberPO.UpdateRequestPO requestPO) {
		BaseMemberVO admin = (BaseMemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		EntMemberVO.DetailEntMemberVO entMember = entMemberDAO.selectEntMemberByNo(requestPO.getEntNo());

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
			.picMblTelno(requestPO.getManagerTelNo())
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
			.managerTelNo(requestPO.getManagerTelNo())
			.managerEmail(requestPO.getManagerEmail())
			.isEmailConsent(requestPO.getIsEmailConsent())
			.build();
	}

	// 사업자 등록번호 중복체크
	public boolean checkBusinessNumber(String businessNumber) {
		return entMemberDAO.isBusinessNumberDuplicate(businessNumber) > 0;
	}

	public DetailResponsePO selectEntMemberByNo(int no) {
		EntMemberVO.DetailEntMemberVO entMember = entMemberDAO.selectEntMemberByNo(no);

		if (ObjectUtils.isEmpty(entMember)) {
			throw new GlobalException(ErrorCode.USER_NOT_FOUND);
		}

		return EntMemberPO.DetailResponsePO.builder()
			.entNo(entMember.getEntMbrNo())
			.companyName(entMember.getConmNm())
			.representativeName(entMember.getRprsvNm())
			.businessNumber(entMember.getBrNo())
			.businessTypeCode(entMember.getBzmnTypeCd())
			.institutionTypeCode(entMember.getInstTypeCd())
			.representativeBusinessCode(entMember.getRprsBzstatCd())
			.representativeIndustryCode(entMember.getRprsTpbizCd())
			.employeeCount(entMember.getEmpCnt())
			.entTelNo(entMember.getTelNo())
			.companyAddress(entMember.getBplcAddress())
			.homepageUrl(entMember.getCoHmpgAddr())
			.mainProduct(entMember.getMainPrdctn())
			.managerName(entMember.getPicNm())
			.managerTypeCode(entMember.getPicSeCd())
			.managerDeptName(entMember.getPicDeptNm())
			.managerPositionCode(entMember.getPicJbpsCd())
			.managerTelNo(entMember.getPicMblTelno())
			.managerEmail(entMember.getEmlAddr())
			.isEmailConsent(entMember.getEmlRcptnAgreYn())
			.sdgnNo(entMember.getSdgnNo())
			.technicalProtectionTotalScore(entMember.getTotalScore())
			.institutionalProtectionScore(entMember.getInstPrtcMngFldScr())
			.personnelProtectionScore(entMember.getPsnlPrtcngFldScr())
			.physicalProtectionScore(entMember.getPhysPrtcMngFldScr())
			.accidentManagementScore(entMember.getAcdntDisMngFldScr())
			.diagnosedAt(DateUtil.formatLocalDateToString(entMember.getLastMdfcnDt()))
			.expiryAt(DateUtil.formatLocalDateToString(entMember.getExpirationDate()))
			.isExpiration(entMember.getExpirationYn())
			.build();
	}

	public EntMemberPO.EmployeeListResponsePO selectEmployeeListByEntNo(int entNo) {
		List<EntMemberVO.EmployeeListResponseVO> employeeList = entMemberDAO.selectEmployeeListByEntNo(entNo);
		AtomicReference<Integer> rowNum = new AtomicReference<>(1);

		return EntMemberPO.EmployeeListResponsePO.builder()
			.totalCount(employeeList.size())
			.list(
				employeeList.stream().map(employee -> EntMemberPO.EmployeeListData.builder()
					.no(rowNum.getAndSet(rowNum.get() + 1))
					.genNo(employee.getMbrNo())
					.typeCode(employee.getSeCd())
					.deptName(employee.getDeptNm())
					.positionCode(employee.getJbpsCd())
					.name(employee.getMbrNm())
					.telno(employee.getMbrMblTelno())
					.email(employee.getEmlAddr())
					.build()).collect(Collectors.toList())
			)
			.build();
	}

	@Transactional
	public void deleteEntMember(int no) {
		EntMemberVO.DetailEntMemberVO entMember = entMemberDAO.selectEntMemberByNo(no);

		if (ObjectUtils.isEmpty(entMember)) {
			throw new GlobalException(ErrorCode.USER_NOT_FOUND);
		}

		//TODO 진행중인 사업이 있을경우 예외 처리

		//진행중인 사업이 없을 경우 삭제
		int result = entMemberDAO.deleteEntMember(no);

		if (result > 0) {
			//기업 소속 회원 정보 테이블에서 소속 직원목록 삭제 여부 'Y' update
			entMemberDAO.updateEmployeesDelYnByEntNo(no);
		}

	}

	@Transactional
   public EntMemberPO.InsertResponsePO insertEntMember(@Valid @RequestBody EntMemberPO.InsertRequestPO requestPO) {
       BaseMemberVO admin = (BaseMemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		EntMemberVO.InsertRequestVO requestVO = EntMemberVO.InsertRequestVO.builder()
			.conmNm(requestPO.getCompanyName())
			.rprsvNm(requestPO.getRepresentativeName())
			.brNo(requestPO.getBusinessNumber())
			.bzmnTypeCd(requestPO.getBusinessTypeCode())
			.instTypeCd(requestPO.getInstitutionTypeCode())
			.rprsBzstatCd(requestPO.getRepresentativeBusinessCode())
			.rprsTpbizCd(requestPO.getRepresentativeBusinessCode())
			.empCnt(requestPO.getEmployeeCount())
			.telNo(requestPO.getEntTelNo())
			// TODO 주소 통으로 들어온걸 우편번호, 도로명, 상세주소 분할해서 저장해야함
			.bplcZip("우편번호")
			.bplcRoadNm("도로명")
			.bplcDaddr("상세주소")
			.coHmpgAddr(requestPO.getHomepageUrl())
			.mainPrdctn(requestPO.getMainProduct())
			.fctYn(requestPO.getIsFactory())
			.picNm(requestPO.getManagerName())
			.picSeCd(requestPO.getManagerTypeCode())
			.picDeptNm(requestPO.getManagerDeptName())
			.picJbpsCd(requestPO.getManagerPositionCode())
			.picMblTelno(requestPO.getManagerTelNo())
			.emlAddr(requestPO.getManagerEmail())
			.emlRcptnAgreYn(requestPO.getIsEmailConsent())
			.build();
		requestVO.setFirst(admin.getMngrId());

		int result = entMemberDAO.insertEntMember(requestVO);

		if(result < 1){
			throw new GlobalException(ErrorCode.INSERT_FAILED);
		}

		// 등록 후 제대로 등록 됐는지 사업자등록번호로 회원 여부 조회 후 프론트로 응답
		EntMemberVO.DefaultEntMemberVO entMember = entMemberDAO.selectEntMemberByBusinessNumber(requestVO.getBrNo());

		if(ObjectUtils.isEmpty(entMember)) {
			throw new GlobalException(ErrorCode.USER_NOT_FOUND);
       }

       return EntMemberPO.InsertResponsePO.builder()
               .companyName(entMember.getConmNm())
               .representativeName(entMember.getRprsvNm())
               .businessNumber(entMember.getBrNo())
               .businessTypeCode(entMember.getBzmnTypeCd())
               .institutionTypeCode(entMember.getInstTypeCd())
               .representativeBusinessCode(entMember.getRprsBzstatCd())
               .representativeIndustryCode(entMember.getRprsTpbizCd())
               .employeeCount(entMember.getEmpCnt())
               .entTelNo(entMember.getTelNo())
               .companyAddress(entMember.getBplcAddress())
               .homepageUrl(entMember.getCoHmpgAddr())
               .mainProduct(entMember.getMainPrdctn())
               .isFactory(entMember.getFctYn())
               .managerName(entMember.getPicNm())
               .managerTypeCode(entMember.getPicSeCd())
               .managerDeptName(entMember.getPicDeptNm())
               .managerPositionCode(entMember.getPicJbpsCd())
               .managerTelNo(entMember.getPicMblTelno())
               .managerEmail(entMember.getEmlAddr())
               .isEmailConsent(entMember.getEmlRcptnAgreYn())
               .build();
   }

}
