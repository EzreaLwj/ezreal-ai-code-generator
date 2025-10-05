package com.ezreal.ai.code.generator.infrastructrue.repo;

import com.ezreal.ai.code.generator.domain.user.model.entity.UserLoginEntity;
import com.ezreal.ai.code.generator.domain.user.repository.UserRepository;
import com.ezreal.ai.code.generator.mapper.UserMapper;
import com.ezreal.ai.code.generator.model.po.User;
import com.mybatisflex.core.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @Resource
    private UserMapper userMapper;

    @Override
    public UserLoginEntity getLoginInfo(String userAccount, String password) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(User::getUserAccount, userAccount);
        queryWrapper.eq(User::getUserPassword, password);
        User user = userMapper.selectOneByQuery(queryWrapper);
        if (user == null) {
            return null;
        }
        return UserLoginEntity.builder()
                .id(user.getId())
                .userName(user.getUserName())
                .userAvatar(user.getUserAvatar())
                .userProfile(user.getUserProfile())
                .createTime(user.getCreateTime())
                .build();
    }

    @Override
    public UserLoginEntity getLoginInfo(Long userId) {
        User user = userMapper.selectOneById(userId);
        if (user != null) {
            return UserLoginEntity.builder()
                    .id(user.getId())
                    .userName(user.getUserName())
                    .userAvatar(user.getUserAvatar())
                    .userProfile(user.getUserProfile())
                    .createTime(user.getCreateTime())
                    .build();
        }
        return null;
    }
}
