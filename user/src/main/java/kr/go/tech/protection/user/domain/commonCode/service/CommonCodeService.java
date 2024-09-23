package kr.go.tech.protection.user.domain.commonCode.service;

import kr.go.tech.protection.user.domain.commonCode.vo.CommonCodeRequestVO;
import kr.go.tech.protection.user.global.common.CommonCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommonCodeService {

    private final CommonCode commonCode;

    public List<CommonCodeRequestVO.RequestList> selectComCodeList(CommonCodeRequestVO.RequestCodeVO reqCodes) {
        String[] reqCoedArr = reqCodes.getReqCode().split(",");
        if(reqCoedArr.length == 0) {
            return null;
        }

        List<CommonCodeRequestVO.RequestList> result = new ArrayList<>();

        for(String code : reqCoedArr) {
            Map<String, String> resultList = commonCode.codeArr.get(code);
            if(resultList!= null && !resultList.isEmpty()){

                CommonCodeRequestVO.CmnDtlCode innerData = null;

                for (Map.Entry<String, String> entry : resultList.entrySet()) {
                    innerData = CommonCodeRequestVO.CmnDtlCode.builder()
                            .dtlCode(entry.getKey())
                            .dtlCodeName(entry.getValue())
                            .build();


                    result.add(CommonCodeRequestVO.RequestList
                            .builder()
                            .groupCode(code)
                            .cmnDtlCode(innerData)
                            .build());
                }
            }
        }
        return result;
    }
}
