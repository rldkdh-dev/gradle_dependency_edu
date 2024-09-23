package kr.go.tech.protection.user.domain.commonCode.mapper;

import kr.go.tech.protection.user.domain.commonCode.vo.CommonCodeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommonCodeMapper {
    List<CommonCodeVO> selectComCodeListAll();
}
