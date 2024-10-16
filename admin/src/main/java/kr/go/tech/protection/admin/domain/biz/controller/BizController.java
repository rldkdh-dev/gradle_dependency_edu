package kr.go.tech.protection.admin.domain.biz.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import kr.go.tech.protection.admin.domain.biz.dto.BizPO;
import kr.go.tech.protection.admin.domain.biz.service.BizService;
import kr.go.tech.protection.admin.global.response.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/bizzes")
public class BizController {
    private final BizService bizService;

    @PostMapping(value = "")
    public ApiResult<BizPO.InsertResponse> insertBiz(@Valid @RequestPart(name="json") BizPO.InsertRequest requestPO,
                                                     @RequestPart(name="file") MultipartFile fileData) throws JsonProcessingException {
        
        BizPO.InsertResponse response = bizService.insertBiz(requestPO, fileData);
        return ApiResult.success(response);
    }
}