package kr.go.tech.protection.admin.domain.account.enterprise.dao;

import java.util.List;
import javax.validation.constraints.NotNull;
import kr.go.tech.protection.admin.domain.account.enterprise.dto.EntMemberPO;
import kr.go.tech.protection.admin.domain.account.enterprise.dto.EntMemberVO;
import kr.go.tech.protection.admin.domain.account.enterprise.dto.EntMemberVO.DefaultEntMemberVO;
import kr.go.tech.protection.admin.domain.account.enterprise.dto.EntMemberVO.UpdateRequestVO;
import kr.go.tech.protection.admin.domain.account.general.dto.GenMemberVO.ResetPasswordRequestVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EntMemberDAO {
    List<EntMemberVO.ListResponseVO> selectEntMemberList(EntMemberPO.SearchPO searchPO);

    EntMemberVO.DefaultEntMemberVO selectEntMemberByNo(int entNo);

    int updateEntMember(EntMemberVO.UpdateRequestVO param);

    int isBusinessNumberDuplicate(String businessNumber);

/*	EntMemberVO.DetailGenMemberVO selectGenMemberByNo(int no);

	EntMemberVO.DefaultGenMemberVO selectGenMemberById(String searchId);

	int updateGenMember(EntMemberVO.UpdateRequestVO param);

	int deleteGenMember(int no);

	int updateEntPrcptMbrInfoDelYn(int no);

	String selectEntMemberNoByBusinessNumber(String businessNumber);

	int updateEntPrcpt(EntMemberVO.UpdateEntPrcptRequestVO param);

	int resetPassword(ResetPasswordRequestVO param);*/

}
