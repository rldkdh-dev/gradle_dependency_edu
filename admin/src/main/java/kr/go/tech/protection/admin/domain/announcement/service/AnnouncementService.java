package kr.go.tech.protection.admin.domain.announcement.service;

import kr.go.tech.protection.admin.domain.announcement.dao.AnnouncementDAO;
import kr.go.tech.protection.admin.domain.announcement.dto.AnnouncementPO;
import kr.go.tech.protection.admin.domain.announcement.dto.AnnouncementVO;
import kr.go.tech.protection.admin.domain.member.dto.BaseMemberVO;
import kr.go.tech.protection.admin.global.exception.ErrorCode;
import kr.go.tech.protection.admin.global.exception.GlobalException;
import kr.go.tech.protection.admin.global.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Slf4j
@Service
@RequiredArgsConstructor
public class AnnouncementService {
    private final AnnouncementDAO announcementDAO;

    // 시간 체크 공통
    public boolean dateValidation(String startDate, String endDate) {
        if(startDate == null || endDate == null) {
            return false;
        }

        Timestamp startTime = DateUtil.convertStringToTimestamp(startDate.replaceAll("-",""));
        Timestamp endTime = DateUtil.convertStringToTimestamp(endDate.replaceAll("-",""));

        // 시작일자보다 종료일자가 이전인 경우
        if(endTime.before(startTime)) {
            return false;
        }

        // 시작일자와 종료일자가 동일한 경우
        if(startTime.equals(endTime)) {
            return false;
        }

        // 시작일자, 종료일자가 오늘보다 이전인 경우
        if(endTime.before(DateUtil.getTodayTimeStamp()) || startTime.before(DateUtil.getTodayTimeStamp())) {
            return false;
        }

        return true;
    }

    @Transactional
    public AnnouncementPO.InsertResponse insertAnnouncement(AnnouncementPO.InsertRequest request) {
        BaseMemberVO admin = (BaseMemberVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Integer recruitCycle = announcementDAO.selectAnnouncementCount(request.getRecruitYear())+1;
        
        // 모집년도와 모집기간 년도 일치여부 확인
        if( !request.getRecruitYear().equals(request.getRecruitStartAt().split("-")[0]) ||
                !request.getRecruitYear().equals(request.getRecruitEndAt().split("-")[0])
        ) {
            throw new GlobalException(ErrorCode.NOT_MATCHED_RECRUIT_DATE);
        }
        
        // 모집기간 벨리데이션 체크
        if(!dateValidation(request.getRecruitStartAt(),request.getRecruitEndAt())) {
            throw new GlobalException(ErrorCode.BAD_REQUEST_ANN_RECRUIT_DATE);
        }
        
        // 위촉기간 벨리데이션 체크
        if(!dateValidation(request.getEntrustingStartAt(),request.getEntrustingEndAt())) {
            throw new GlobalException(ErrorCode.BAD_REQUEST_ANN_ENTRUSTING_DATE);
        }

        // 위촉 시작일이 모집 종료일보다 빠른지 체크
        if(DateUtil.convertStringToTimestamp(request.getEntrustingStartAt().replaceAll("-",""))
                .before(DateUtil.convertStringToTimestamp(request.getRecruitEndAt().replaceAll("-","")))) {
            throw new GlobalException(ErrorCode.BAD_REQUEST_ANN_ENTRUSTING_START_DATE);
        }

        AnnouncementVO.InsertRequest param = AnnouncementVO.InsertRequest.builder()
                .rcrtYr(request.getRecruitYear())
                .rcrtFldCd(request.getRecruitFiled())
                .rcrtCycl(recruitCycle)
                .rcrtBgngDt(DateUtil.convertStringToTimestamp(request.getRecruitStartAt().replaceAll("-","")))
                .rcrtEndDt(DateUtil.convertStringToTimestamp(request.getRecruitEndAt().replaceAll("-","")))
                .entrstBgngDt(DateUtil.convertStringToTimestamp(request.getEntrustingStartAt().replaceAll("-","")))
                .entrstEndDt(DateUtil.convertStringToTimestamp(request.getEntrustingEndAt().replaceAll("-","")))
                .pstgYn(request.getPostingYn().equals("Y")?"Y":"N")
                .build();
        param.setFirst(admin.getMngrId());
        param.setLast(admin.getMngrId());

        int result = announcementDAO.insertAnnouncement(param);
        if(result < 1) {
            throw new GlobalException(ErrorCode.ANNOUNCEMENT_INSERT_FAILED);
        }

        return AnnouncementPO.InsertResponse.builder()
                .recruitYear(request.getRecruitYear())
                .recruitFiled(request.getRecruitFiled())
                .recruitCycle(recruitCycle)
                .recruitStartAt(request.getRecruitStartAt())
                .recruitEndAt(request.getRecruitEndAt())
                .entrustingStartAt(request.getEntrustingStartAt())
                .entrustingEndAt(request.getEntrustingEndAt())
                .postingYn(request.getPostingYn().equals("Y")?"Y":"N")
                .build();
    }
}
