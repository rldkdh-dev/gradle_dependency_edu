package kr.go.tech.protection.admin.domain.biz.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.go.tech.protection.admin.domain.biz.dao.BizMapper;
import kr.go.tech.protection.admin.domain.biz.dto.BizPO;
import kr.go.tech.protection.admin.domain.biz.dto.BizVO;
import kr.go.tech.protection.admin.domain.member.dto.BaseMemberVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BizService {
    private final BizMapper bizMapper;

    @Transactional
    public BizPO.InsertResponse insertBiz(BizPO.InsertRequest requestPO, MultipartFile fileData) throws JsonProcessingException {

        BaseMemberVO admin = (BaseMemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LocalDateTime now = LocalDateTime.now();
        Integer bizNo = requestPO.getBizNo();
        
        // TODO 테스트용
        Integer fileNo = null;

        // JSON to String
        ObjectMapper objectMapper = new ObjectMapper();
        String processJsn = objectMapper.writeValueAsString(requestPO.getBizProcessJson());
        String formJsn = objectMapper.writeValueAsString(requestPO.getApplicationFormJson());

        // TODO 파일 업로드 (파일 있는 경우 파일 업로드)
        if(!fileData.isEmpty()) {
        }

        String statusCode = getStatusCode(getLocaldatetime(requestPO.getRecruitStartDt()),getLocaldatetime(requestPO.getRecruitEndDt()),now);


        // 임시저장 체크
        BizVO.DefaultTempSave temp = bizMapper.selectTempSave(requestPO.getBizNo());
        
        if(temp == null) {
            // TODO 임시저장 데이터가 없는 경우 처리
        }

        // 임시저장 DELETE
        int delResult = bizMapper.deleteTempSave(requestPO.getBizNo());
        if(delResult < 1) {
            // TODO 임시저장 삭제 에러 처리
        }

        // 사업 UPDATE
        BizVO.DefaultBiz requestVO = BizVO.DefaultBiz.builder()
            .pageNo(requestPO.getPageNo())
            .bizNo(bizNo)
            .fileNo(fileNo)
            .bizNm(requestPO.getBizName())
            .tkcgDeptCd(Arrays.stream(requestPO.getDepts())
                    .map(String::valueOf)
                    .collect(Collectors.joining("|")))
            .picId(requestPO.getAdminId())
            .rcrtBgngDt(getLocaldatetime(requestPO.getRecruitStartDt()))
            .rcrtEndDt(getLocaldatetime(requestPO.getRecruitEndDt()))
            .bizBgngDt(getLocaldatetime(requestPO.getBizStartDt()))
            .bizEndDt(getLocaldatetime(requestPO.getBizEndDt()))
            .bizTrgtCd(String.join("|",requestPO.getTarget()))
            .bizCn(requestPO.getBizContent())
            .rcrtSttsCd(statusCode)
            .bizSttsCd(statusCode)
            .bizSmrCn(requestPO.getBizSummary())
            .aplyPrcCn(requestPO.getApplicationProcess())
            .cntInfCn(requestPO.getContact())
            .prgrsPrcsJsn(processJsn)
            .aplyFrmJsn(formJsn)
            .strgYn("Y")
            .tmprStrgYn("N")
            .build();

        requestVO.setFirst(admin.getMngrId());
        requestVO.setLast(admin.getMngrId());

        int result = bizMapper.updateBiz(requestVO);
        if(result < 1) {
            // TODO update 에러 처리
        }

        // 약관이 있는 경우 약관 INSERT
        if(!requestPO.getTerms().isEmpty()) {
            // 기존 임시저장된 약관 삭제
            int termResult = bizMapper.deleteTerms(bizNo);
            if(termResult < 1) {
                // TODO 약관 삭제 에러 처리
            }

            for (BizPO.Terms term: requestPO.getTerms()) {
                BizVO.InsertTerm data = BizVO.InsertTerm.builder()
                        .bizNo(bizNo)
                        .trmsNm(term.getTermsName())
                        .trmsCn(term.getTermsContent())
                        .sortNo(term.getSortNo())
                        .build();
                data.setFirst(admin.getMngrId());
                data.setLast(admin.getMngrId());

                int termsRst = bizMapper.insertTerms(data);
                if(termsRst < 1) {
                    // TODO INSERT 에러 처리
                }
            }
        }

        return BizPO.InsertResponse.builder()
                .bizNo(bizNo)
                .bizName(requestPO.getBizName())
                .adminId(requestPO.getAdminId())
                .depts(requestPO.getDepts())
                .recruitStartDt(requestPO.getRecruitStartDt())
                .recruitEndDt(requestPO.getRecruitEndDt())
                .bizStartDt(requestPO.getBizStartDt())
                .bizEndDt(requestPO.getBizEndDt())
                .target(requestPO.getTarget())
                .bizContent(requestPO.getBizContent())
                .fileName(fileData.getName())
                .bizSummary(requestPO.getBizSummary())
                .applicationProcess(requestPO.getApplicationProcess())
                .contact(requestPO.getContact())
                .bizProcessJson(requestPO.getBizProcessJson())
                .terms(requestPO.getTerms())
                .applicationFormJson(requestPO.getApplicationFormJson())
                .pageNo(requestPO.getPageNo())
                .build();
    }

    public LocalDateTime getLocaldatetime(Date date) {
        if(date != null) {
            return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
        return null;
    }

    public String getStatusCode(LocalDateTime startDt, LocalDateTime endDt, LocalDateTime now) {
        if(startDt.isBefore(now) && endDt.isAfter(now)) {
            return  "STS003"; // 모집중
        } else if(startDt.isAfter(now)) {
            return "STS002"; // 모집전
        } else if(now.isAfter(endDt)) {
            return "STS004"; // 모집종료
        }
        return null;
    }

    @Transactional
    public BizPO.InsertResponse insertTempSaveBiz(BizPO.TempInsertRequest requestPO) throws JsonProcessingException {

        BaseMemberVO admin = (BaseMemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        LocalDateTime now = LocalDateTime.now();
        String statusCode = getStatusCode(getLocaldatetime(requestPO.getRecruitStartDt()),getLocaldatetime(requestPO.getRecruitEndDt()),now);
        Integer bizNo = requestPO.getBizNo();

        // JSON to String
        ObjectMapper objectMapper = new ObjectMapper();
        String processJsn = objectMapper.writeValueAsString(requestPO.getBizProcessJson());
        String formJsn = objectMapper.writeValueAsString(requestPO.getApplicationFormJson());

        // 사업 UPDATE
        BizVO.DefaultBiz requestVO = BizVO.DefaultBiz.builder()
                .bizNo(bizNo)
                .pageNo(requestPO.getPageNo())
                .bizNm(requestPO.getBizName())
                .tkcgDeptCd(Arrays.stream(requestPO.getDepts())
                        .map(String::valueOf)
                        .collect(Collectors.joining("|")))
                .picId(requestPO.getAdminId())
                .rcrtBgngDt(getLocaldatetime(requestPO.getRecruitStartDt()))
                .rcrtEndDt(getLocaldatetime(requestPO.getRecruitEndDt()))
                .bizBgngDt(getLocaldatetime(requestPO.getBizStartDt()))
                .bizEndDt(getLocaldatetime(requestPO.getBizEndDt()))
                .bizTrgtCd(String.join("|",requestPO.getTarget()))
                .bizCn(requestPO.getBizContent())
                .rcrtSttsCd(statusCode)
                .bizSttsCd(statusCode)
                .bizSmrCn(requestPO.getBizSummary())
                .aplyPrcCn(requestPO.getApplicationProcess())
                .cntInfCn(requestPO.getContact())
                .prgrsPrcsJsn(processJsn)
                .aplyFrmJsn(formJsn)
                .strgYn("N")
                .tmprStrgYn("Y")
                .build();

        // 임시저장 데이터 확인 (임시저장이 있는 경우 임시저장 및 사업 데이터 UPDATE, 없는 경우 임시저장, 사업 데이터 INSERT)
        BizVO.DefaultTempSave tempSave = bizMapper.selectTempSave(bizNo);

        if(tempSave != null) {
            // 약관 페이지 제외 공고 UPDATE 처리
            if(!requestPO.getPageNo().equals(3)) {
                requestVO.setLast(admin.getMngrId());
                int result = bizMapper.updateBiz(requestVO);
                if( result < 1 ) {
                }
            }

            // 임시저장 UPDATE
            BizVO.DefaultTempSave param = BizVO.DefaultTempSave.builder()
                    .tmprTtl(requestPO.getTempTitle())
                    .bizNo(requestVO.getBizNo())
                    .build();
            param.setLast(admin.getMngrId());
            int tempResult = bizMapper.mergeIntoTempSave(param);
            if(tempResult < 1) {
                // TODO 에러 처리
            }
        } else {
            // 공고 INSERT
            requestVO.setFirst(admin.getMngrId());
            requestVO.setLast(admin.getMngrId());
            int result = bizMapper.insertBiz(requestVO);
            if( result < 1 ) {
                // TODO insert 에러 처리
            }

            // 임시저장 INSERT
            BizVO.DefaultTempSave param = BizVO.DefaultTempSave.builder()
                    .tmprTtl(requestPO.getTempTitle())
                    .bizNo(requestVO.getBizNo())
                    .build();
            param.setFirst(admin.getMngrId());
            param.setLast(admin.getMngrId());
            int tempResult = bizMapper.mergeIntoTempSave(param);
            if(tempResult < 1) {
                // TODO 에러 처리
            }
        }

        // 약관 페이지 요청시 약관 INSERT
        if(requestPO.getPageNo().equals(3) && !requestPO.getTerms().isEmpty()) {
            // 기존 임시저장된 약관 삭제
            int termResult = bizMapper.deleteTerms(requestVO.getBizNo());
            if(termResult < 1) {
                // TODO 약관 삭제 에러 처리
            }

            for (BizPO.Terms term: requestPO.getTerms()) {
                BizVO.InsertTerm data = BizVO.InsertTerm.builder()
                        .bizNo(requestVO.getBizNo())
                        .trmsNm(term.getTermsName())
                        .trmsCn(term.getTermsContent())
                        .sortNo(term.getSortNo())
                        .build();
                data.setFirst(admin.getMngrId());
                data.setLast(admin.getMngrId());

                int termsRst = bizMapper.insertTerms(data);
                if (termsRst < 1) {
                    // TODO INSERT 에러 처리
                }
            }
        }

        return BizPO.InsertResponse.builder()
                .bizNo(bizNo)
                .bizName(requestPO.getBizName())
                .adminId(requestPO.getAdminId())
                .depts(requestPO.getDepts())
                .recruitStartDt(requestPO.getRecruitStartDt())
                .recruitEndDt(requestPO.getRecruitEndDt())
                .bizStartDt(requestPO.getBizStartDt())
                .bizEndDt(requestPO.getBizEndDt())
                .target(requestPO.getTarget())
                .bizContent(requestPO.getBizContent())
                .bizSummary(requestPO.getBizSummary())
                .applicationProcess(requestPO.getApplicationProcess())
                .contact(requestPO.getContact())
                .bizProcessJson(requestPO.getBizProcessJson())
                .terms(requestPO.getTerms())
                .applicationFormJson(requestPO.getApplicationFormJson())
                .pageNo(requestPO.getPageNo())
                .build();
    }
}
