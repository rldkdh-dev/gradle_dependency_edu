package kr.go.tech.protection.admin.domain.biz.dao;

import kr.go.tech.protection.admin.domain.biz.dto.BizPO;
import kr.go.tech.protection.admin.domain.biz.dto.BizVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BizDAO {
    int updateBizBeforeInsert(BizVO.DefaultBiz requestVO);

    int insertBiz(BizVO.DefaultBiz requestVO);

    int insertTerms(BizVO.InsertTerm data);

    BizVO.DefaultTempSave selectTempSave(Integer bizNo);

    int deleteTempSave(Integer bizNo);

    int deleteTerms(Integer bizNo);

    int mergeIntoTempSave(BizVO.DefaultTempSave updateParam);

    List<BizVO.ListResponse> selectBizList(BizPO.SearchRequest request);

    BizVO.BizDetail findBizByBizNo(Integer bizNo);

    int updateBizAfterInsert(BizVO.UpdateBiz requestVO);

    List<BizPO.Terms> findBizTermsByBizNo(Integer bizNo);
}
