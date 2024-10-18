package kr.go.tech.protection.admin.global.common;

import kr.go.tech.protection.admin.domain.auth.dto.AuthPO;
import kr.go.tech.protection.admin.domain.auth.service.AuthInitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthGroup {

    private final AuthInitService authInitService;
    public List<AuthPO.NameResponsePO> authArray = new ArrayList<>();

    @PostConstruct
    public void init() {
        authArray = authInitService.selectAuthNameList();
        log.info("///////////// AuthGroup INIT //////////////");
    }

    public void syncDepartment() {
        authArray.clear();
        authInitService.selectAuthNameList().forEach(dept->
                authArray.add(
                        AuthPO.NameResponsePO.builder()
                                .adminAuthGroupNo(dept.getAdminAuthGroupNo())
                                .adminAuthGroupName(dept.getAdminAuthGroupName()).build()
                ));
    }
}
