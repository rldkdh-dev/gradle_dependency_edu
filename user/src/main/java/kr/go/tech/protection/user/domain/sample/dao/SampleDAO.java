package kr.go.tech.protection.user.domain.sample.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import kr.go.tech.protection.user.domain.commonCode.vo.CommonCodeVO;

@Mapper
public interface SampleDAO {

	List<CommonCodeVO> test();
}
