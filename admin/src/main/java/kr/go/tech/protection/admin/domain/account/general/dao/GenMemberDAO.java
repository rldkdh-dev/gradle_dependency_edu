package kr.go.tech.protection.admin.domain.account.general.dao;

import java.util.List;
import kr.go.tech.protection.admin.domain.account.general.dto.GenMemberPO;
import kr.go.tech.protection.admin.domain.account.general.dto.GenMemberVO;
import kr.go.tech.protection.admin.domain.account.general.dto.GenMemberVO.InsertEntPrcptVO;
import kr.go.tech.protection.admin.domain.account.general.dto.GenMemberVO.InsertRequestVO;
import kr.go.tech.protection.admin.domain.account.general.dto.GenMemberVO.ResetPasswordRequestVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface GenMemberDAO {
    List<GenMemberVO.ListResponseVO> selectGenMemberList(GenMemberPO.SearchPO searchPO);

	GenMemberVO.DetailGenMemberVO selectGenMemberByNo(int no);

	GenMemberVO.DefaultGenMemberVO selectGenMemberById(String searchId);

	int updateGenMember(GenMemberVO.UpdateRequestVO param);

	int deleteGenMember(int no);

	int updateEntPrcptMbrInfoDelYnByGenNo(int no);

	String selectEntMemberNoByBusinessNumber(String businessNumber);

	int updateEntPrcpt(GenMemberVO.UpdateEntPrcptRequestVO param);

	int resetPassword(ResetPasswordRequestVO param);

	int isGenIdDuplicate(String searchId);

	int insertGenMember(InsertRequestVO requestVO);

	int selectEntMbrNoByBusinessNumber(String businessNumber);

	int insertGenMemberIntoEntPrcpt(InsertEntPrcptVO entPrcptVO);
}
