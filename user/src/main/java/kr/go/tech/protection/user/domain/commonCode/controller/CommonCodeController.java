package kr.go.tech.protection.user.domain.commonCode.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kr.go.tech.protection.user.domain.commonCode.service.CommonCodeService;
import kr.go.tech.protection.user.domain.commonCode.vo.CommonCodeRequestVO;
import kr.go.tech.protection.user.global.response.ApiResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/codes")
public class CommonCodeController {
    private final CommonCodeService commonCodeService;

    @GetMapping(value="")
    public ApiResult<List<CommonCodeRequestVO.RequestList>> selectCmnCode(CommonCodeRequestVO.RequestCodeVO requestCodeVO) {
        List<CommonCodeRequestVO.RequestList> responses = commonCodeService.selectComCodeList(requestCodeVO);

        return ApiResult.success(responses);
    }
}
