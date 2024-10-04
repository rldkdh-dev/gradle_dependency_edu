package kr.go.tech.protection.admin.domain.member.dao;

import kr.go.tech.protection.admin.domain.member.dto.AdminPO;
import kr.go.tech.protection.admin.domain.member.dto.AdminVO;
import kr.go.tech.protection.admin.domain.member.dto.BaseAdminVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface AdminDAO {
    List<AdminVO.ListResponseVO> selectAdminMemberList(AdminPO.SearchPO searchPO);

    AdminVO.MemberVO selectAdminMemberByNo(int no);

    Integer deleteAdminMember(int no);

    AdminVO.MemberVO selectAdminMemberById(String searchId);

    int insertAdminMember(AdminVO.RegRequestVO requestVO);

    int updatePassword(AdminVO.PasswordRequestVO param);

    // 로그인 회원정보 찾기
    Optional<BaseAdminVO> selectLoginMemberById(String id);

    int resetPassword(AdminVO.ResetPasswordRequestVO param);

    int updateAdminMember(AdminVO.UpdateRequestVO param);
}
