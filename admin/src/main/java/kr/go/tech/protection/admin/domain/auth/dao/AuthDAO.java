package kr.go.tech.protection.admin.domain.auth.dao;

import kr.go.tech.protection.admin.domain.auth.dto.AuthVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AuthDAO {

    List<AuthVO.NameListResponseVO> selectAuthList();
}
