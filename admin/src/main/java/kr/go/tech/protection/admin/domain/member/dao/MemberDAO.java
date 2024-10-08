package kr.go.tech.protection.admin.domain.member.dao;

import kr.go.tech.protection.admin.domain.member.dto.MemberPO;
import kr.go.tech.protection.admin.domain.member.dto.MemberVO;
import kr.go.tech.protection.admin.domain.member.dto.BaseMemberVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberDAO {
    List<MemberVO.ListResponseVO> selectAdminMemberList(MemberPO.SearchPO searchPO);

    MemberVO.DefaultMemberVO selectAdminMemberByNo(int no);

    Integer deleteAdminMember(int no);

    MemberVO.DefaultMemberVO selectAdminMemberById(String searchId);

    int insertAdminMember(MemberVO.RegRequestVO requestVO);

    int updatePassword(MemberVO.PasswordRequestVO param);

    // 로그인 회원정보 찾기
    Optional<BaseMemberVO> selectLoginMemberById(String id);

    int resetPassword(MemberVO.ResetPasswordRequestVO param);

    int updateAdminMember(MemberVO.UpdateRequestVO param);
}
