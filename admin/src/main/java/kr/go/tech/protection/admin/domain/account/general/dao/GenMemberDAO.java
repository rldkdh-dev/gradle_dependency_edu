package kr.go.tech.protection.admin.domain.account.general.dao;

import java.util.List;
import kr.go.tech.protection.admin.domain.account.general.dto.GenMemberPO;
import kr.go.tech.protection.admin.domain.account.general.dto.GenMemberVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GenMemberDAO {
    List<GenMemberVO.ListResponseVO> selectGenMemberList(GenMemberPO.SearchPO searchPO);

	GenMemberVO.DetailGenMemberVO selectGenMemberByNo(int no);

	GenMemberVO.DefaultGenMemberVO selectGenMemberById(String searchId);

	int updateGenMember(GenMemberVO.UpdateRequestVO param);

	Integer deleteGenMember(int no);
}
