package kr.go.tech.protection.admin.domain.auth.service;

import kr.go.tech.protection.admin.domain.auth.dao.AuthDAO;
import kr.go.tech.protection.admin.domain.auth.dto.AuthPO;
import kr.go.tech.protection.admin.domain.auth.dto.AuthVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final AuthDAO authDAO;

	public List<AuthPO.NameResponsePO> selectAuthNameList() {
		List<AuthVO.NameListResponseVO> authList = authDAO.selectAuthNameList();

		return authList.stream()
				.map(auth->AuthPO.NameResponsePO.builder()
						.authNo(auth.getAuthrtNo())
						.authName(auth.getAuthrtNm())
						.build())
						.collect(Collectors.toList());
	}
}
