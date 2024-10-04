package kr.go.tech.protection.admin.domain.login.controller;

import kr.go.tech.protection.admin.domain.login.dto.LoginPO;
import kr.go.tech.protection.admin.domain.login.service.LoginService;
import kr.go.tech.protection.admin.global.response.ApiResult;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/login")
public class LoginController {
    private final LoginService loginService;

    @PostMapping("")
    public ApiResult<LoginPO.LoginResponsePO> login(@RequestBody @Valid LoginPO.LoginRequestPO requestPO, HttpServletResponse response) {

        return ApiResult.success(loginService.login(requestPO, response));
    }
}
