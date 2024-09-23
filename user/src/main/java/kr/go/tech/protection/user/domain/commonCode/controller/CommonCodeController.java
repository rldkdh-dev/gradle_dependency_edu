package kr.go.tech.protection.user.domain.commonCode.controller;


import kr.go.tech.protection.user.domain.commonCode.service.CommonCodeService;
import kr.go.tech.protection.user.domain.commonCode.vo.CommonCodeRequestVO;
import kr.go.tech.protection.user.global.common.ResponseWrapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cmnCode")
public class CommonCodeController {
    private final CommonCodeService commonCodeService;

    @RequestMapping(value="/getCode", method = RequestMethod.POST)
    public ResponseEntity<ResponseWrapper> selectCmnCode(@RequestBody CommonCodeRequestVO.RequestCodeVO requestCodeVO) {
        List<CommonCodeRequestVO.RequestList> data = commonCodeService.selectComCodeList(requestCodeVO);
        ResponseWrapper responseWrapper = ResponseWrapper.builder()
                .code(200)
                .message("200")
                .data(data)
                .build();
        return ResponseEntity.ok(responseWrapper);
    }
}
