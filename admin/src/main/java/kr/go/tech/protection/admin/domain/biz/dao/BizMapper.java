package kr.go.tech.protection.admin.domain.biz.dao;

import kr.go.tech.protection.admin.domain.biz.dto.BizVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BizMapper {
    int updateBiz(BizVO.DefaultBiz requestVO);

    int insertBiz(BizVO.DefaultBiz requestVO);

    int insertTerms(BizVO.InsertTerm data);

    BizVO.DefaultTempSave selectTempSave(Integer bizNo);

    int deleteTempSave(Integer bizNo);

    int deleteTerms(Integer bizNo);

    int mergeIntoTempSave(BizVO.DefaultTempSave updateParam);
}
