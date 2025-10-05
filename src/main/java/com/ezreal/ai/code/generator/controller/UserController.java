package com.ezreal.ai.code.generator.controller;

import com.ezreal.ai.code.generator.common.Response;
import com.ezreal.ai.code.generator.domain.user.model.UserLoginRequest;
import com.ezreal.ai.code.generator.domain.user.model.entity.UserLoginEntity;
import com.ezreal.ai.code.generator.enums.ResponseCode;
import com.ezreal.ai.code.generator.exception.AppException;
import com.ezreal.ai.code.generator.domain.user.model.UserRegisterRequest;
import com.ezreal.ai.code.generator.utils.ResultUtils;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.ezreal.ai.code.generator.model.po.User;
import com.ezreal.ai.code.generator.domain.user.service.UserService;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 控制层。
 *
 * @author <a href="https://github.com/EzreaLwj">程序员Ezreal</a>
 */
@Tag(name = "用户")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登陆
     *
     * @param request 请求体
     * @return 用户ID
     */
    @PostMapping("login")
    @Operation(summary = "登陆")
    public Response<UserLoginEntity> login(@RequestBody UserLoginRequest request, HttpServletRequest httpServletRequest) {
        if (request == null) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), "参数为空");
        }
        UserLoginEntity userLoginEntity = userService.login(request, httpServletRequest);
        return ResultUtils.success(userLoginEntity);
    }

    /**
     * 注册
     *
     * @param request 请求体
     * @return 用户ID
     */
    @PostMapping("register")
    @Operation(summary = "注册")
    public Response<Long> register(@RequestBody UserRegisterRequest request) {
        if (request == null) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), "参数为空");
        }
        Long userId = userService.register(request);
        return ResultUtils.success(userId);
    }

    /**
     * 登陆
     *
     * @param httpServletRequest 请求体
     * @return 用户登陆信息
     */
    @PostMapping("getLoginUser")
    @Operation(summary = "获取用户登陆信息")
    public Response<UserLoginEntity> getLoginUser
    (HttpServletRequest httpServletRequest) {
        UserLoginEntity userLoginEntity = userService.getLoginUser(httpServletRequest);
        return ResultUtils.success(userLoginEntity);
    }
}
