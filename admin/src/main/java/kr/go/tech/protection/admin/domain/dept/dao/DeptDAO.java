package kr.go.tech.protection.admin.domain.dept.dao;

import kr.go.tech.protection.admin.domain.dept.dto.DeptVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DeptDAO {

    List<DeptVO.NameListResponseVO> selectDeptNameList();
}
