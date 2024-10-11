package kr.go.tech.protection.admin.domain.account.general.dao;

import java.util.List;
import kr.go.tech.protection.admin.domain.account.general.dto.GenMemberPO;
import kr.go.tech.protection.admin.domain.account.general.dto.GenMemberVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GenMemberDAO {
    List<GenMemberVO.ListResponseVO> selectGenMemberList(GenMemberPO.SearchPO searchPO);

}
