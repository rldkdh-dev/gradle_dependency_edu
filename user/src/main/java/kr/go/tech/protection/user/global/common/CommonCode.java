package kr.go.tech.protection.user.global.common;

import kr.go.tech.protection.user.domain.commonCode.service.CommonCodeInitService;
import kr.go.tech.protection.user.domain.commonCode.vo.CommonCodeVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class CommonCode {

    private final CommonCodeInitService comService;
    public Map<String, Map<String, String>> codeArr = new HashMap<>();

    @PostConstruct
    public void init() {
        List<CommonCodeVO> list = comService.selectComCodeListAll();
        list.forEach(code -> {
            // 현재 cmnCd에 대한 inner 맵 가져오기
            Map<String, String> inner = codeArr.computeIfAbsent(code.getCmnCd(), k -> new HashMap<>());

            // inner 맵에 값 추가
            inner.put(code.getCmnCdDtlCd(), code.getCmnCdDtlNm());
        });

        log.info("///////////////// COMMON CODE INIT /////////////////");
    }

    // Common Code 초기화
    public void syncCommonCode() {
        codeArr.clear();
        comService.selectComCodeListAll().forEach(code-> codeArr.put(code.getCmnCd(), codeArr.get(code.getCmnCd())));
    }
}
