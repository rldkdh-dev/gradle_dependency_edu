package kr.go.tech.protection.admin.domain.dept.service;

import kr.go.tech.protection.admin.domain.dept.dao.DeptDAO;
import kr.go.tech.protection.admin.domain.dept.dto.DeptPO;
import kr.go.tech.protection.admin.domain.dept.dto.DeptVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DeptInitService {
	private final DeptDAO deptDAO;

	public List<DeptPO.NameResponsePO> selectDeptNameList() {
		List<DeptVO.NameListResponseVO> deptList = deptDAO.selectDeptNameList();

		return deptList.stream()
				.map(auth-> DeptPO.NameResponsePO.builder()
						.adminDepartmentNo(auth.getAuthrtNo())
						.adminDepartmentName(auth.getAuthrtNm())
						.build())
						.collect(Collectors.toList());
	}
}
