package kr.go.tech.protection.admin.domain.account.enterprise.dao;

import java.util.List;
import kr.go.tech.protection.admin.domain.account.enterprise.dto.EntMemberPO;
import kr.go.tech.protection.admin.domain.account.enterprise.dto.EntMemberVO;
import kr.go.tech.protection.admin.domain.account.enterprise.dto.EntMemberVO.InsertRequestVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EntMemberDAO {
    List<EntMemberVO.ListResponseVO> selectEntMemberList(EntMemberPO.SearchPO searchPO);

    EntMemberVO.DetailEntMemberVO selectEntMemberByNo(int entNo);

    int updateEntMember(EntMemberVO.UpdateRequestVO param);

    int isBusinessNumberDuplicate(String businessNumber);

    List<EntMemberVO.EmployeeListResponseVO> selectEmployeeListByEntNo(int entNo);

    int deleteEntMember(int no);

    int updateEmployeesDelYnByEntNo(int no);

    int insertEntMember(EntMemberVO.InsertRequestVO requestVO);

    EntMemberVO.DefaultEntMemberVO selectEntMemberByBusinessNumber(String businessNumber);
}
