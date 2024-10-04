package kr.go.tech.protection.admin.domain.login.dao;

import kr.go.tech.protection.admin.domain.login.dto.RefreshToken;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RefreshTokenDAO extends CrudRepository<RefreshToken, String> {
    Optional<RefreshToken> findByLoginId(String loginId);
}
