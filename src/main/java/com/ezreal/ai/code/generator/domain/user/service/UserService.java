package com.ezreal.ai.code.generator.domain.user.service;

import com.ezreal.ai.code.generator.domain.user.model.UserLoginRequest;
import com.ezreal.ai.code.generator.domain.user.model.UserRegisterRequest;
import com.ezreal.ai.code.generator.domain.user.model.entity.UserLoginEntity;
import com.mybatisflex.core.service.IService;
import com.ezreal.ai.code.generator.model.po.User;
import jakarta.servlet.http.HttpServletRequest;

/**
 *  服务层。
 *
 * @author <a href="https://github.com/EzreaLwj">程序员Ezreal</a>
 */
public interface UserService extends IService<User> {

    UserLoginEntity login(UserLoginRequest request, HttpServletRequest httpServletRequest);

    Long register(UserRegisterRequest userRegisterRequest);

    UserLoginEntity getLoginUser(HttpServletRequest httpServletRequest);


}
