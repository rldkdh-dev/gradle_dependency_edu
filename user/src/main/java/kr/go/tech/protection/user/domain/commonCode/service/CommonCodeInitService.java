package kr.go.tech.protection.user.domain.commonCode.service;

import kr.go.tech.protection.user.domain.commonCode.mapper.CommonCodeMapper;
import kr.go.tech.protection.user.domain.commonCode.vo.CommonCodeVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommonCodeInitService {

    private final CommonCodeMapper commonCodeMapper;

    public List<CommonCodeVO> selectComCodeListAll() {
        List<CommonCodeVO> list = commonCodeMapper.selectComCodeListAll();
        return list;
    }
}
