package com.ezreal.ai.code.generator.domain.user.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.ezreal.ai.code.generator.domain.user.model.UserLoginRequest;
import com.ezreal.ai.code.generator.domain.user.model.entity.UserLoginEntity;
import com.ezreal.ai.code.generator.domain.user.repository.UserRepository;
import com.ezreal.ai.code.generator.enums.Constants;
import com.ezreal.ai.code.generator.enums.ResponseCode;
import com.ezreal.ai.code.generator.exception.AppException;
import com.ezreal.ai.code.generator.domain.user.model.UserRegisterRequest;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.ezreal.ai.code.generator.model.po.User;
import com.ezreal.ai.code.generator.mapper.UserMapper;
import com.ezreal.ai.code.generator.domain.user.service.UserService;
import dev.langchain4j.agent.tool.P;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 服务层实现。
 *
 * @author <a href="https://github.com/EzreaLwj">程序员Ezreal</a>
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserRepository userRepository;

    @Override
    public UserLoginEntity login(UserLoginRequest request, HttpServletRequest httpServletRequest) {
        String userAccount = request.getUserAccount();
        String userPassword = request.getUserPassword();
        if (StrUtil.isBlankIfStr(userAccount) || StrUtil.isBlankIfStr(userPassword)) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode());
        }


        UserLoginEntity loginInfo = userRepository.getLoginInfo(userAccount, userPassword);
        if (loginInfo == null) {
            log.error("用户账号或者密码错误，request：{}", JSONUtil.toJsonStr(request));
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), "用户账号或者密码错误");
        }
        HttpSession session = httpServletRequest.getSession();
        session.setAttribute(Constants.USER_STATE, User.builder().id(loginInfo.getId()).build());

        return loginInfo;
    }

    @Override
    public Long register(UserRegisterRequest userRegisterRequest) {
        String userAccount = userRegisterRequest.getUserAccount();
        String password = userRegisterRequest.getPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();

        if (userAccount == null || userAccount.length() < 6) {
            log.error("账号长度小于6位，account：{}", userAccount);
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), "账号长度小于6位");
        }
        if (password == null || password.length() < 6) {
            log.error("密码长度小于6位，password：{}", password);
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), "密码长度小于6位");
        }
        if (checkPassword == null || !checkPassword.equals(password)) {
            log.error("密码不一致，password：{}，checkPassword：{}", password, checkPassword);
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), "密码不一致");
        }

        QueryWrapper queryWrapper = query().eq(User::getUserAccount, userAccount);

        User user = getOne(queryWrapper);
        if (user != null) {
            log.error("账号已存在，account：{}", userAccount);
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), "账号已存在");
        }


        user = User.builder()
                .userAccount(userAccount)
                .userPassword(password)
                .userName(RandomUtil.randomString(5))
                .userRole("user")
                .build();
        save(user);
        return user.getId();
    }

    @Override
    public UserLoginEntity getLoginUser(HttpServletRequest httpServletRequest) {
        Object userState = httpServletRequest.getSession().getAttribute("user_state");
        User user = (User) userState;
        if (user == null) {
            throw new AppException(ResponseCode.USER_NOT_LOGIN.getCode());
        }
        Long id = user.getId();
        UserLoginEntity loginInfo = userRepository.getLoginInfo(id);
        if (loginInfo == null) {
            throw new AppException(ResponseCode.USER_NOT_LOGIN.getCode());
        }
        return loginInfo;
    }
}
