package com.ezreal.ai.code.generator.domain.user.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.ezreal.ai.code.generator.enums.ResponseCode;
import com.ezreal.ai.code.generator.exception.AppException;
import com.ezreal.ai.code.generator.domain.user.model.UserRegisterRequest;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.ezreal.ai.code.generator.model.po.User;
import com.ezreal.ai.code.generator.mapper.UserMapper;
import com.ezreal.ai.code.generator.domain.user.service.UserService;
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
}
