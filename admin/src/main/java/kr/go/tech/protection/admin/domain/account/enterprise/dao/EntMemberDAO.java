package kr.go.tech.protection.admin.domain.account.enterprise.dao;

import java.util.List;
import kr.go.tech.protection.admin.domain.account.enterprise.dto.EntMemberPO;
import kr.go.tech.protection.admin.domain.account.enterprise.dto.EntMemberVO;
import kr.go.tech.protection.admin.domain.account.general.dto.GenMemberVO.ResetPasswordRequestVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EntMemberDAO {
    List<EntMemberVO.ListResponseVO> selectEntMemberList(EntMemberPO.SearchPO searchPO);

/*	EntMemberVO.DetailGenMemberVO selectGenMemberByNo(int no);

	EntMemberVO.DefaultGenMemberVO selectGenMemberById(String searchId);

	int updateGenMember(EntMemberVO.UpdateRequestVO param);

	int deleteGenMember(int no);

	int updateEntPrcptMbrInfoDelYn(int no);

	String selectEntMemberNoByBusinessNumber(String businessNumber);

	int updateEntPrcpt(EntMemberVO.UpdateEntPrcptRequestVO param);

	int resetPassword(ResetPasswordRequestVO param);*/

}
