package kr.go.tech.protection.admin.domain.biz.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import kr.go.tech.protection.admin.domain.biz.dao.BizDAO;
import kr.go.tech.protection.admin.domain.biz.dto.BizPO;
import kr.go.tech.protection.admin.domain.biz.dto.BizVO;
import kr.go.tech.protection.admin.domain.member.dto.BaseMemberVO;
import kr.go.tech.protection.admin.global.exception.ErrorCode;
import kr.go.tech.protection.admin.global.exception.GlobalException;
import kr.go.tech.protection.admin.global.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BizService {
    private final BizDAO bizDao;

    @Transactional
    public BizPO.InsertResponse insertBiz(BizPO.InsertRequest requestPO) throws JsonProcessingException {
        BaseMemberVO admin = (BaseMemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer bizNo = requestPO.getBizNo();
        BizVO.BizDetail biz = bizDao.findBizByBizNo(bizNo);
        if(biz == null) {
            throw new GlobalException(ErrorCode.NOT_FOUND_BIZ);
        }

        if(!biz.getPicId().equals(admin.getMngrId())) {
            throw new GlobalException(ErrorCode.NOT_MATCHED_PIC);
        }

        // JSON to String
        ObjectMapper objectMapper = new ObjectMapper();
        String formJsn = objectMapper.writeValueAsString(requestPO.getApplicationFormJson());

        if(!requestPO.getPageNo().equals(4)) {
            throw new GlobalException(ErrorCode.BAD_REQUEST_PAGE_NO);
        }

        // 임시저장 체크
        BizVO.DefaultTempSave temp = bizDao.selectTempSave(requestPO.getBizNo());
        if(temp == null) {
            throw new GlobalException(ErrorCode.NOT_FOUND_TEMP_SAVE);
        }

        // 임시저장 DELETE
        int delResult = bizDao.deleteTempSave(requestPO.getBizNo());
        if(delResult < 1) {
            throw new GlobalException(ErrorCode.NOT_FOUND_TEMP_SAVE);
        }

        // 사업 UPDATE
        BizVO.DefaultBiz requestVO = BizVO.DefaultBiz.builder()
            .pageNo(requestPO.getPageNo())
            .bizNo(bizNo)
            .aplyFrmJsn(formJsn)
            .strgYn("Y")
            .tmprStrgYn("N")
            .build();

        requestVO.setFirst(admin.getMngrId());
        requestVO.setLast(admin.getMngrId());

        int result = bizDao.updateBizBeforeInsert(requestVO);
        if(result < 1) {
            throw new GlobalException(ErrorCode.BIZ_INSERT_FAILED);
        }

        return BizPO.InsertResponse.builder()
                .bizNo(bizNo)
                .pageNo(requestPO.getPageNo())
                .build();
    }

    @Transactional
    public BizPO.InsertResponse insertTempSaveBiz(BizPO.TempInsertRequest requestPO, MultipartFile fileData) throws JsonProcessingException {

        BaseMemberVO admin = (BaseMemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer bizNo = requestPO.getBizNo();

        if(bizNo != null) {
            BizVO.BizDetail biz = bizDao.findBizByBizNo(bizNo);
            if(biz == null) {
                throw new GlobalException(ErrorCode.NOT_FOUND_BIZ);
            }

            if(!admin.getMngrId().equals(biz.getPicId())){
                throw new GlobalException(ErrorCode.NOT_MATCHED_PIC);
            }
        }

        String statusCode = "";

        // 사업 정보 페이지 요청일때만 상태값 추출
        if(requestPO.getPageNo().equals(1)) {
            LocalDateTime now = LocalDateTime.now();
            statusCode = getStatusCode(getLocaldatetime(requestPO.getRecruitStartDt()),getLocaldatetime(requestPO.getRecruitEndDt()),now);
        }

        // 프로세스 설정시 첫번째 블록이 접수인지, 마지막 블록이 종료인지 확인
        if(     requestPO.getPageNo().equals(2) &&
                (!requestPO.getBizProcessJson().get(0).getProcessCode().equals("PRO001") ||
                !requestPO.getBizProcessJson().get(requestPO.getBizProcessJson().size()-1).getProcessCode().equals("PRO010"))) {
            throw new GlobalException(ErrorCode.BAD_REQUEST_PROCESS_BLOCK);
        }
        
        if (!fileData.isEmpty()) {
            // TODO 파일 업로드 추가
        }

        // JSON to String
        ObjectMapper objectMapper = new ObjectMapper();
        String processJsn = objectMapper.writeValueAsString(requestPO.getBizProcessJson());
        String formJsn = objectMapper.writeValueAsString(requestPO.getApplicationFormJson());

        // 사업 UPDATE
        BizVO.DefaultBiz requestVO = BizVO.DefaultBiz.builder()
                .bizNo(bizNo)
                .pageNo(requestPO.getPageNo())
                .bizNm(requestPO.getBizName())
                .tkcgDeptCd(requestPO.getDepts()==null?null:
                        Arrays.stream(requestPO.getDepts())
                        .map(String::valueOf)
                        .collect(Collectors.joining("|")))
                .picId(admin.getMngrId())
                .rcrtBgngDt(getLocaldatetime(requestPO.getRecruitStartDt()))
                .rcrtEndDt(getLocaldatetime(requestPO.getRecruitEndDt()))
                .bizBgngDt(getLocaldatetime(requestPO.getBizStartDt()))
                .bizEndDt(getLocaldatetime(requestPO.getBizEndDt()))
                .bizTrgtCd(requestPO.getTarget()==null?null:String.join("|",requestPO.getTarget()))
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
        requestVO.setLast(admin.getMngrId());

        // 임시저장 데이터 확인 (임시저장이 있는 경우 임시저장 및 사업 데이터 UPDATE, 없는 경우 임시저장, 사업 데이터 INSERT)
        BizVO.DefaultTempSave tempSave = bizDao.selectTempSave(bizNo);

        if(tempSave != null) {
            // 약관 페이지 제외 공고 UPDATE 처리
            if(!requestPO.getPageNo().equals(3)) {
                requestVO.setLast(admin.getMngrId());
                int result = bizDao.updateBizBeforeInsert(requestVO);
                if( result < 1 ) {
                    throw new GlobalException(ErrorCode.BIZ_UPDATE_FAILED);
                }
            }

            // 임시저장 UPDATE
            BizVO.DefaultTempSave param = BizVO.DefaultTempSave.builder()
                    .tmprTtl(requestPO.getTempTitle())
                    .bizNo(requestVO.getBizNo())
                    .build();
            param.setLast(admin.getMngrId());
            int tempResult = bizDao.mergeIntoTempSave(param);
            if(tempResult < 1) {
                throw new GlobalException(ErrorCode.TEMP_SAVE_INSERT_FAILED);
            }
        } else {
            // 공고 INSERT
            requestVO.setFirst(admin.getMngrId());
            requestVO.setLast(admin.getMngrId());
            int result = bizDao.insertBiz(requestVO);
            if( result < 1 ) {
                throw new GlobalException(ErrorCode.BIZ_INSERT_FAILED);
            }

            bizNo = requestVO.getBizNo();

            // 임시저장 INSERT
            BizVO.DefaultTempSave param = BizVO.DefaultTempSave.builder()
                    .tmprTtl(requestPO.getTempTitle())
                    .bizNo(bizNo)
                    .build();
            param.setFirst(admin.getMngrId());
            param.setLast(admin.getMngrId());
            int tempResult = bizDao.mergeIntoTempSave(param);
            if(tempResult < 1) {
                throw new GlobalException(ErrorCode.TEMP_SAVE_INSERT_FAILED);
            }
        }

        // 약관 페이지 요청시 약관 INSERT
        if(requestPO.getPageNo().equals(3) && !requestPO.getTerms().isEmpty()) {
            insertTerms(bizNo, admin,requestPO.getTerms());
        }

        return BizPO.InsertResponse.builder()
                .bizNo(bizNo)
                .pageNo(requestPO.getPageNo())
                .build();
    }

    public List<BizPO.ListResponse> selectBizzesList(BizPO.SearchRequest request) {

        List<BizVO.ListResponse> bizList = bizDao.selectBizList(request);
        AtomicReference<Integer> rowNum = new AtomicReference<>(1);

        return bizList.stream().map(biz->BizPO.ListResponse.builder()
                .no(rowNum.getAndSet(rowNum.get() + 1))
                .bizNo(biz.getBizNo())
                .bizName(biz.getBizNm())
                .assignDepartment(biz.getDeptNm())
                .picName(biz.getMngrNm())
                .recruitmentDate(DateUtil.formatLocalDateToString(biz.getRcrtBgngDt())+ " ~ " +DateUtil.formatLocalDateToString(biz.getRcrtEndDt()))
                .bizDate(DateUtil.formatLocalDateToString(biz.getBizBgngDt()) + " ~ " + DateUtil.formatLocalDateToString(biz.getBizEndDt()))
                .applicantsCount(biz.getApplicantsCount())
                .recruitmentStatus(biz.getRcrtBgngDt().isBefore(LocalDateTime.now()) ?"모집 중":"대기")
                .build()
        ).collect(Collectors.toList());
    }

    @Transactional
    public BizPO.UpdateResponse updateBizzes(BizPO.UpdateRequest request, MultipartFile fileData) throws JsonProcessingException {
        BizVO.BizDetail biz = bizDao.findBizByBizNo(request.getBizNo());
        if(biz==null) {
            throw new GlobalException(ErrorCode.NOT_FOUND_BIZ);
        }

        BaseMemberVO admin = (BaseMemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Integer bizNo = biz.getBizNo();
        LocalDate today = LocalDate.now();
        // 모집시작 여부
        String afterRecruitDateYn = today.equals(biz.getRcrtBgngDt().toLocalDate())?"Y":today.isAfter(biz.getRcrtBgngDt().toLocalDate())?"Y":"N";
        // 공고상태
        String statusCode = "";

        // 담당자 일치 확인
        if(!admin.getMngrId().equals(biz.getPicId())) {
            throw new GlobalException(ErrorCode.NOT_MATCHED_PIC);
        }

        if(request.getPageNo().equals(1)) {
            // 모집종료일자가 모집시작일자보다 빠른지 확인
            if(request.getRecruitEndDt().before(java.sql.Timestamp.valueOf(biz.getRcrtBgngDt()))) {
                throw new GlobalException(ErrorCode.BAD_REQUEST_RECRUIT_DATE);
            }

            LocalDate recruitEndDt = getLocalDateFormat(request.getRecruitEndDt());

            // 모집종료일자가 오늘 이전인지 확인
            if(!recruitEndDt.equals(today) && request.getRecruitEndDt().before(new Date())) {
                throw new GlobalException(ErrorCode.BAD_REQUEST_RECRUIT_DATE_FOR_TODAY);
            }

            // 본인 담당부서 포함여부 확인
            if(!Arrays.stream(request.getDepts()).anyMatch(dept-> dept.equals(admin.getAuthrtGroupNo()))) {
                throw new GlobalException(ErrorCode.BAD_REQUEST_MY_DEPT);
            }

            LocalDateTime now = LocalDateTime.now();
            statusCode = getStatusCode(biz.getRcrtBgngDt(),getLocaldatetime(request.getRecruitEndDt()),now); // 상태 코드
        }

        if(afterRecruitDateYn.equals("Y") && !request.getPageNo().equals(1)) {
            throw new GlobalException(ErrorCode.BAD_REQUEST_UPDATE_PAGE_NO);
        }

        // 프로세스 설정시 첫번째 블록이 접수인지, 마지막 블록이 종료인지 확인
        if(     request.getPageNo().equals(2) &&
                (!request.getBizProcessJson().get(0).getProcessCode().equals("PRO001") ||
                        !request.getBizProcessJson().get(request.getBizProcessJson().size()-1).getProcessCode().equals("PRO010"))) {
            throw new GlobalException(ErrorCode.BAD_REQUEST_PROCESS_BLOCK);
        }

        // JSON to String
        ObjectMapper objectMapper = new ObjectMapper();
        String processJsn = "";
        String formJsn = "";

        if(request.getPageNo().equals(2)) {
            processJsn = objectMapper.writeValueAsString(request.getBizProcessJson());
        } else if(request.getPageNo().equals(4)) {
            formJsn = objectMapper.writeValueAsString(request.getApplicationFormJson());
        }

        // 사업 UPDATE
        BizVO.UpdateBiz requestVO = BizVO.UpdateBiz.builder()
                .afterBizStartDt(afterRecruitDateYn)
                .pageNo(request.getPageNo())
                .bizNo(bizNo)
                .bizNm(request.getBizName())
                .tkcgDeptCd(request.getDepts()==null?null:
                        Arrays.stream(request.getDepts())
                                .map(String::valueOf)
                                .collect(Collectors.joining("|")))
                .rcrtEndDt(getLocaldatetime(request.getRecruitEndDt()))
                .bizBgngDt(getLocaldatetime(request.getBizStartDt()))
                .bizEndDt(getLocaldatetime(request.getBizEndDt()))
                .bizCn(request.getBizContent())
                .rcrtSttsCd(statusCode)
                .bizSttsCd(statusCode)
                .bizTrgtCd(request.getTarget()==null?null:String.join("|",request.getTarget()))
                .bizSmrCn(request.getBizSummary())
                .aplyPrcCn(request.getApplicationProcess())
                .cntInfCn(request.getContact())
                .prgrsPrcsJsn(processJsn)
                .aplyFrmJsn(formJsn)
                .build();
        requestVO.setLast(admin.getMngrId());

        int result = bizDao.updateBizAfterInsert(requestVO);
        if(result < 1) {
            throw new GlobalException(ErrorCode.BIZ_UPDATE_FAILED);
        }

        // 약관 페이지 수정 요청이면서 모집시작 전인 경우 약관 INSERT
        if(request.getPageNo().equals(3) && !request.getTerms().isEmpty()) {
            insertTerms(bizNo, admin, request.getTerms());
        }

        // TODO 파일 추가
        if(!fileData.isEmpty()) {
        }

        // TODO 파일 추가
        return BizPO.UpdateResponse.builder()
                .bizNo(request.getBizNo())
                .pageNo(request.getPageNo())
                .build();
    }

    public BizPO.BizDetail selectBizByBizNo(Integer bizNo,Integer pageNo) throws JsonProcessingException {
        BaseMemberVO admin = (BaseMemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BizVO.BizDetail biz = bizDao.findBizByBizNo(bizNo);
        if(biz == null) {
            throw new GlobalException(ErrorCode.NOT_FOUND_BIZ);
        }

        if(!biz.getPicId().equals(admin.getMngrId()) || !Arrays.stream(biz.getTkcgDeptCd().split("\\|")).anyMatch(dept->dept.equals(admin.getAuthrtGroupNo().toString()))) {
            throw new GlobalException(ErrorCode.INVALID_DEPT_GROUP);
        }

        ObjectMapper objectMapper = new ObjectMapper();

        if(pageNo == 1) {
            String[] deptArr = biz.getTkcgDeptCd().split("\\|");
            BizPO.BizInfoPage bizInfoPage = BizPO.BizInfoPage.builder()
                    .bizNo(bizNo)
                    .bizName(biz.getBizNm())
                    .adminName(admin.getMngrNm())
                    .depts(Arrays.stream(deptArr)
                            .map(Integer::parseInt)
                            .toArray(Integer[]::new))
                    .recruitStartDt(DateUtil.formatLocalDateToString(biz.getRcrtBgngDt()))
                    .recruitEndDt(DateUtil.formatLocalDateToString(biz.getRcrtEndDt()))
                    .bizStartDt(DateUtil.formatLocalDateToString(biz.getBizBgngDt()))
                    .bizEndDt(DateUtil.formatLocalDateToString(biz.getBizEndDt()))
                    .target(biz.getBizTrgtCd().split("\\|"))
                    .bizContent(biz.getBizCn())
                    .bizSummary(biz.getBizSmrCn())
                    .applicationProcess(biz.getAplyPrcCn())
                    .contact(biz.getCntInfCn())
                    .build();

            return BizPO.BizDetail.builder()
                    .pageNo(pageNo)
                    .bizNo(bizNo)
                    .bizInfoPage(bizInfoPage)
                    .build();
        }else if(pageNo == 2) {
            List<BizPO.BizProcess> processes = objectMapper.readValue(biz.getPrgrsPrcsJsn(), new TypeReference<List<BizPO.BizProcess>>() {});

            return BizPO.BizDetail.builder()
                    .bizNo(bizNo)
                    .pageNo(pageNo)
                    .bizProcessPage(BizPO.BizProcessPage.builder()
                            .bizProcessJson(processes)
                            .build())
                    .build();
        }else if(pageNo == 3) {
            List<BizPO.Terms> terms = bizDao.findBizTermsByBizNo(bizNo);

            return BizPO.BizDetail.builder()
                    .bizNo(bizNo)
                    .pageNo(pageNo)
                    .bizTermsPage(BizPO.BizTermsPage.builder().terms(terms).build())
                    .build();
        }else if(pageNo == 4) {
            List<BizPO.BizForm> bizForms = objectMapper.readValue(biz.getAplyFrmJsn(), new TypeReference<List<BizPO.BizForm>>() {});

            return BizPO.BizDetail.builder()
                    .bizNo(bizNo)
                    .pageNo(pageNo)
                    .bizFormPage(BizPO.BizFormPage.builder().applicationFormJson(bizForms).build())
                    .build();
        }else {
            throw new GlobalException(ErrorCode.BAD_REQUEST_PAGE_NO);
        }
    }

    public void insertTerms(Integer bizNo, BaseMemberVO admin, List<BizPO.Terms> terms) {
        // 기존 임시저장된 약관 삭제
        bizDao.deleteTerms(bizNo);

        for (BizPO.Terms term: terms) {
            BizVO.InsertTerm data = BizVO.InsertTerm.builder()
                    .bizNo(bizNo)
                    .trmsNm(term.getTermsName())
                    .trmsCn(term.getTermsContent())
                    .sortNo(term.getSortNo())
                    .build();
            data.setFirst(admin.getMngrId());
            data.setLast(admin.getMngrId());

            int termsRst = bizDao.insertTerms(data);
            if(termsRst < 1) {
                throw new GlobalException(ErrorCode.TERMS_INSERT_FAILED);
            }
        }
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

    public LocalDate getLocalDateFormat(Date param) {
        return param.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public void deleteBizByBizNo(Integer bizNo) {
        BaseMemberVO admin = (BaseMemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        BizVO.BizDetail biz = bizDao.findBizByBizNo(bizNo);
        if(biz == null) {
            throw new GlobalException(ErrorCode.NOT_FOUND_BIZ);
        }

        if(!biz.getPicId().equals(admin.getMngrId())) {
            throw new GlobalException(ErrorCode.NOT_MATCHED_PIC);
        }

        BizVO.DeleteBiz delBiz = BizVO.DeleteBiz.builder()
                .bizNo(bizNo)
                .build();
        delBiz.setLast(admin.getMngrId());

        int result = bizDao.deleteBiz(delBiz);
        if(result < 1) {
            throw new GlobalException(ErrorCode.BIZ_DELETE_FAILED);
        }
    }
}
