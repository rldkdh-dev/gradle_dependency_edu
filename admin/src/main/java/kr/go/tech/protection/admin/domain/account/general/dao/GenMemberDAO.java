package kr.go.tech.protection.admin.domain.account.general.dao;

import java.util.List;
import kr.go.tech.protection.admin.domain.account.general.dto.GenMemberPO;
import kr.go.tech.protection.admin.domain.account.general.dto.GenMemberVO;
import kr.go.tech.protection.admin.domain.member.dto.MemberVO.DefaultMemberVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GenMemberDAO {
    List<GenMemberVO.ListResponseVO> selectGenMemberList(GenMemberPO.SearchPO searchPO);

	GenMemberVO.DetailGenMemberVO selectGenMemberByNo(int no);

	Integer deleteGenMember(int no);
}
