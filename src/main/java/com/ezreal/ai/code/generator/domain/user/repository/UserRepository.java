package com.ezreal.ai.code.generator.domain.user.repository;

import com.ezreal.ai.code.generator.domain.user.model.entity.UserLoginEntity;

public interface UserRepository {

    UserLoginEntity getLoginInfo(String userAccount, String password);

    UserLoginEntity getLoginInfo(Long userId);
}
