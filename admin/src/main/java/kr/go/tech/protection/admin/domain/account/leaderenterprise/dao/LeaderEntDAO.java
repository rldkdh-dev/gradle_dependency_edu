package kr.go.tech.protection.admin.domain.account.leaderenterprise.dao;

import java.util.List;
import kr.go.tech.protection.admin.domain.account.leaderenterprise.dto.LeaderEntPO.SearchPO;
import kr.go.tech.protection.admin.domain.account.leaderenterprise.dto.LeaderEntVO;
import kr.go.tech.protection.admin.domain.account.leaderenterprise.dto.LeaderEntVO.DetailEntMemberVO;
import kr.go.tech.protection.admin.domain.account.leaderenterprise.dto.LeaderEntVO.EntInfoResponseVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface LeaderEntDAO {

	List<LeaderEntVO.ListResponseVO> selectLeaderEntList(SearchPO searchPO);

	LeaderEntVO.DetailEntMemberVO selectLeaderEntByNo(int no);

	LeaderEntVO.EntInfoResponseVO selectEntInfoByBusinessNumber(String businessNumber);



/*    List<GenMemberVO.ListResponseVO> selectGenMemberList(GenMemberPO.SearchPO searchPO);

	GenMemberVO.DetailGenMemberVO selectGenMemberByNo(int no);

	GenMemberVO.DefaultGenMemberVO selectGenMemberById(String searchId);

	int updateGenMember(GenMemberVO.UpdateRequestVO param);

	int deleteGenMember(int no);

	int updateEntPrcptMbrInfoDelYnByGenNo(int no);

	Integer selectEntMemberNoByBusinessNumber(String businessNumber);

	int updateEntPrcpt(GenMemberVO.UpdateEntPrcptRequestVO param);

	int resetPassword(ResetPasswordRequestVO param);

	int isGenIdDuplicate(String searchId);

	int insertGenMember(InsertRequestVO requestVO);

	int selectEntMbrNoByBusinessNumber(String businessNumber);

	int insertGenMemberIntoEntPrcpt(InsertEntPrcptVO entPrcptVO);*/
}
