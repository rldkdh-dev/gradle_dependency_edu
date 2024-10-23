package kr.go.tech.protection.admin.domain.account.general.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import javax.validation.Valid;
import kr.go.tech.protection.admin.domain.account.general.dao.GenMemberDAO;
import kr.go.tech.protection.admin.domain.account.general.dto.GenMemberPO;
import kr.go.tech.protection.admin.domain.account.general.dto.GenMemberVO;
import kr.go.tech.protection.admin.domain.account.general.dto.GenMemberVO.DetailGenMemberVO;
import kr.go.tech.protection.admin.domain.account.general.dto.GenMemberVO.InsertEntPrcptVO;
import kr.go.tech.protection.admin.domain.account.general.dto.GenMemberVO.UpdateEntPrcptRequestVO;
import kr.go.tech.protection.admin.domain.member.dto.BaseMemberVO;
import kr.go.tech.protection.admin.global.exception.ErrorCode;
import kr.go.tech.protection.admin.global.exception.GlobalException;
import kr.go.tech.protection.admin.global.util.DateUtil;
import kr.go.tech.protection.admin.global.util.NumberUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;

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
					.genNo(genMember.getMbrNo())
					.companyName(genMember.getConmNm())
					.genName(genMember.getMbrNm())
					.genId(genMember.getMbrId())
					.genPhone(genMember.getMbrMblTelno())
					.genEmail(genMember.getEmlAddr())
					.createdAt(DateUtil.formatLocalDateToString(genMember.getFrstRegDt()))
					.modifiedAt(DateUtil.formatLocalDateToString(genMember.getLastMdfcnDt()))
					.build()).collect(Collectors.toList())
			)
			.build();
	}

	public GenMemberPO.DetailResponsePO selectGenMemberByNo(int no) {
		//회원정보 상세 조회 및 소속기업 정보 조회
		GenMemberVO.DetailGenMemberVO genMember = genMemberDAO.selectGenMemberByNo(no);

		if (ObjectUtils.isEmpty(genMember)) {
			throw new GlobalException(ErrorCode.USER_NOT_FOUND);
		}

		return GenMemberPO.DetailResponsePO.builder()
			//회원정보
			.genNo(genMember.getMbrNo())
			.genName(genMember.getMbrNm())
			.genderCd(genMember.getMbrGndrCd())
			.birthDate(genMember.getMbrBrdt())
			.genId(genMember.getMbrId())
			.genPhone(genMember.getMbrMblTelno())
			.emailConsentYn(genMember.getEmlRcptnAgreYn())
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
			.companyRoadName(genMember.getBplcRoadNm())
			.companyDetailAddress(genMember.getBplcDaddr())
			.companyAddress(genMember.getCompanyAddress())
			.allowYn(genMember.getAlwYn())
			.build();
	}


	@Transactional
	public GenMemberPO.UpdateResponsePO updateGenMember(GenMemberPO.UpdateRequestPO requestPO) {
		BaseMemberVO admin = (BaseMemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		GenMemberVO.DetailGenMemberVO genMember = genMemberDAO.selectGenMemberByNo(requestPO.getGenNo());

		if (ObjectUtils.isEmpty(genMember)) {
			throw new GlobalException(ErrorCode.USER_NOT_FOUND);
		}

		// 수정하려는 사업자 등록번호 값 검증
		if (requestPO.getBusinessNumber() != null && !requestPO.getBusinessNumber().isEmpty()) {
			Integer entMemberNo = genMemberDAO.selectEntMemberNoByBusinessNumber(requestPO.getBusinessNumber());

			if (entMemberNo != null) {
				GenMemberVO.UpdateEntPrcptRequestVO updateEntPrcptRequestParam = UpdateEntPrcptRequestVO.builder()
					.entMbrNo(entMemberNo)
					.mbrNo(requestPO.getGenNo())
					.build();

				updateEntPrcptRequestParam.setLast(admin.getMngrId());

				int result = genMemberDAO.updateEntPrcpt(updateEntPrcptRequestParam);
				if (result < 1) {
					throw new GlobalException(ErrorCode.NO_UPDATE);
				}

			} else {
				throw new GlobalException(ErrorCode.NO_BUSINESS_NUMBER);
			}

		}

		GenMemberVO.UpdateRequestVO param = GenMemberVO.UpdateRequestVO.builder()
			.mbrNo(requestPO.getGenNo())
			.mbrNm(requestPO.getGenName())
			.mbrGndrCd(requestPO.getGenderCd())
			.mbrBrdt(DateUtil.convertStringToTimestamp(requestPO.getBirthDate()))
			.mbrId(requestPO.getGenId())
			.mbrMblTelno(requestPO.getGenPhone())
			.emlAddr(requestPO.getGenEmail())
			.emlRcptnAgreYn(requestPO.getEmailConsentYn())
			.homeZip(requestPO.getZipCode())
			.homeRoadNm(requestPO.getRoadName())
			.homeDaddr(requestPO.getDetailAddress())
			.build();
		param.setLast(admin.getMngrId());

		int result = genMemberDAO.updateGenMember(param);

		if (result < 1) {
			throw new GlobalException(ErrorCode.NO_UPDATE);
		}

		return GenMemberPO.UpdateResponsePO.builder()
			.genNo(requestPO.getGenNo())
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

	@Transactional
	public void deleteGenMember(int no) {
		GenMemberVO.DetailGenMemberVO genMember = genMemberDAO.selectGenMemberByNo(no);

		if (ObjectUtils.isEmpty(genMember)) {
			throw new GlobalException(ErrorCode.USER_NOT_FOUND);
		}

		//TODO 진행중인 사업이 있을경우 예외 처리

		int result = genMemberDAO.deleteGenMember(no);

		if (result > 0) {
			//기업 소속 회원 정보 테이블에서 삭제 여부 'Y' update
			genMemberDAO.updateEntPrcptMbrInfoDelYnByGenNo(no);
		}

	}

	@Transactional
	public GenMemberPO.ResetPasswordResponsePO resetPassword(GenMemberPO.ResetPasswordRequestPO requestPO) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

		GenMemberVO.DefaultGenMemberVO genMember = genMemberDAO.selectGenMemberById(requestPO.getGenId());

		if (ObjectUtils.isEmpty(genMember)) {
			throw new GlobalException(ErrorCode.USER_NOT_FOUND);
		}

		// 로그인 된 관리자 회원
		BaseMemberVO admin = (BaseMemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		// 12자리 비밀번호 난수 생성
		String tempPassword = NumberUtil.generateTempPassword();

		GenMemberVO.ResetPasswordRequestVO param = GenMemberVO.ResetPasswordRequestVO.builder()
			.mbrId(requestPO.getGenId())
			.mbrPswd(encoder.encode(tempPassword))
			.mbrPswdChgDt(LocalDateTime.now())
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
			throw new GlobalException(ErrorCode.PASSWORD_RESET_FAILED);
		}

		return GenMemberPO.ResetPasswordResponsePO.builder()
			.genNo(genMember.getMbrNo())
			.genId(requestPO.getGenId())
			.build();
	}

	@Transactional
   public GenMemberPO.InsertResponsePO insertGenMember(@Valid @RequestBody GenMemberPO.InsertRequestPO requestPO) {
       BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
       BaseMemberVO admin = (BaseMemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		GenMemberVO.InsertRequestVO requestVO = GenMemberVO.InsertRequestVO.builder()
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
		requestVO.setFirst(admin.getMngrId());

		int result = genMemberDAO.insertGenMember(requestVO);

		if(result > 0){
			//사업자 등록번호 조회 후 존재하면 해당 기업회원 번호로 일반회원 기업 소속 회원정보 테이블에 등록
			int entNo = genMemberDAO.selectEntMbrNoByBusinessNumber(requestPO.getBusinessNumber());
			if (entNo > 0) {
				InsertEntPrcptVO entPrcptVO = InsertEntPrcptVO.builder()
					.mbrNo(requestVO.getMbrNo())
					.entMbrNo(entNo)
					// TODO 기업 소속 정보 테이블의 구분코드, 직위코드, 부서명은 어떤걸로 넣어야하는지 ? CHECK
					.build();
				entPrcptVO.setFirst(admin.getMngrId());
				if (genMemberDAO.insertGenMemberIntoEntPrcpt(entPrcptVO) < 1) {
					throw new GlobalException(ErrorCode.INSERT_FAILED);
				}
			}
		}else{
			throw new GlobalException(ErrorCode.INSERT_FAILED);
		}

		// 등록 후 제대로 등록 됐는지 아이디로 회원 여부 조회 후 프론트로 응답
		GenMemberVO.DefaultGenMemberVO genMember = genMemberDAO.selectGenMemberById(requestVO.getMbrId());

		if(ObjectUtils.isEmpty(genMember)) {
			throw new GlobalException(ErrorCode.USER_NOT_FOUND);
       }

       return GenMemberPO.InsertResponsePO.builder()
               .genNo(genMember.getMbrNo())
               .genName(genMember.getMbrNm())
               .genderCd(genMember.getMbrGndrCd())
               .birthDate(genMember.getMbrBrdt())
               .genId(genMember.getMbrId())
               .genPhone(genMember.getMbrMblTelno())
               .genEmail(genMember.getEmlAddr())
               .emailConsentYn(genMember.getEmlRcptnAgreYn())
               .zipCode(genMember.getHomeZip())
               .roadName(genMember.getHomeRoadNm())
               .detailAddress(genMember.getHomeDaddr())
               .build();
   }

	public boolean checkGenMemberIdDuplicate(String searchId) {
		return genMemberDAO.isGenIdDuplicate(searchId) > 0;
	}

}


