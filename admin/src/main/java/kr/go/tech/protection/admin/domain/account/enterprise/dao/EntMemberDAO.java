package kr.go.tech.protection.admin.domain.account.enterprise.dao;

import java.util.List;
import kr.go.tech.protection.admin.domain.account.enterprise.dto.EntMemberPO;
import kr.go.tech.protection.admin.domain.account.enterprise.dto.EntMemberVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EntMemberDAO {
    List<EntMemberVO.ListResponseVO> selectEntMemberList(EntMemberPO.SearchPO searchPO);

    EntMemberVO.DetailEntMemberVO selectEntMemberByNo(int entNo);

    int updateEntMember(EntMemberVO.UpdateRequestVO param);

    int isBusinessNumberDuplicate(String businessNumber);

    List<EntMemberVO.EmployeeListResponseVO> selectEmployeeListByEntNo(int entNo);

/*	EntMemberVO.DetailGenMemberVO selectGenMemberByNo(int no);

	EntMemberVO.DefaultGenMemberVO selectGenMemberById(String searchId);

	int updateGenMember(EntMemberVO.UpdateRequestVO param);

	int deleteGenMember(int no);

	int updateEntPrcptMbrInfoDelYn(int no);

	String selectEntMemberNoByBusinessNumber(String businessNumber);

	int updateEntPrcpt(EntMemberVO.UpdateEntPrcptRequestVO param);

	int resetPassword(ResetPasswordRequestVO param);*/

}
