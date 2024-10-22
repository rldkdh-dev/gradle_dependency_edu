package kr.go.tech.protection.admin.domain.biz.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import kr.go.tech.protection.admin.domain.biz.dto.BizPO;
import kr.go.tech.protection.admin.domain.biz.service.BizService;
import kr.go.tech.protection.admin.global.response.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

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

    @PostMapping(value = "/temp")
    public ApiResult<BizPO.InsertResponse> insertTempSaveBiz(@Valid @RequestPart(name="json") BizPO.TempInsertRequest requestPO) throws JsonProcessingException {
        BizPO.InsertResponse response = bizService.insertTempSaveBiz(requestPO);
        return ApiResult.success(response);
    }

    @GetMapping(value = "")
    public ApiResult<List<BizPO.ListResponse>> selectBizzesList(@Valid @RequestBody BizPO.SearchRequest request) {
        List<BizPO.ListResponse> response = bizService.selectBizzesList(request);
        return ApiResult.success(response);
    }

    @PutMapping(value = "")
    public ApiResult<BizPO.UpdateResponse> updateBizzes( @Valid @RequestPart(name="json") BizPO.UpdateRequest request,
                                                         @RequestPart(name="file") MultipartFile fileData) throws JsonProcessingException {
        BizPO.UpdateResponse response = bizService.updateBizzes(request,fileData);
        return ApiResult.success(response);
    }

    @GetMapping(value = "/{bizNo}/{pageNo}")
    public ApiResult<BizPO.BizDetail> selectBizByBizNo( @PathVariable Integer bizNo, @PathVariable Integer pageNo ) throws JsonProcessingException {
        BizPO.BizDetail response = bizService.selectBizByBizNo(bizNo, pageNo);
        return ApiResult.success(response);
    }

}
