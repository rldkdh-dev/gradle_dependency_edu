package kr.go.tech.protection.admin.domain.member.dao;

import kr.go.tech.protection.admin.domain.member.dto.AdminVO;
import kr.go.tech.protection.admin.domain.member.dto.AdminPO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AdminDAO {
    List<AdminVO.ListResponseVO> selectAdminMemberList(AdminPO.SearchPO searchPO);

    AdminVO.MemberVO selectAdminMemberByNo(int no);

    Integer deleteAdminMember(int no);

    AdminVO.MemberVO selectAdminMemberById(String searchId);
}
