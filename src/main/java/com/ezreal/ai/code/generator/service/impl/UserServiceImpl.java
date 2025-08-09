package com.ezreal.ai.code.generator.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.ezreal.ai.code.generator.model.po.User;
import com.ezreal.ai.code.generator.mapper.UserMapper;
import com.ezreal.ai.code.generator.service.UserService;
import org.springframework.stereotype.Service;

/**
 * 服务层实现。
 *
 * @author <a href="https://github.com/EzreaLwj">程序员Ezreal</a>
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
