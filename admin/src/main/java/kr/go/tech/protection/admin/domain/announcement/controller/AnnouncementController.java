package kr.go.tech.protection.admin.domain.announcement.controller;

import kr.go.tech.protection.admin.domain.announcement.dto.AnnouncementPO;
import kr.go.tech.protection.admin.domain.announcement.service.AnnouncementService;
import kr.go.tech.protection.admin.global.response.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/announcement")
public class AnnouncementController {
    private final AnnouncementService announcementService;

    @PostMapping("")
    public ApiResult<AnnouncementPO.InsertResponse> insertAnnouncement(@Valid @RequestBody AnnouncementPO.InsertRequest request) {
        AnnouncementPO.InsertResponse response = announcementService.insertAnnouncement(request);
        return ApiResult.success(response);
    }
}
