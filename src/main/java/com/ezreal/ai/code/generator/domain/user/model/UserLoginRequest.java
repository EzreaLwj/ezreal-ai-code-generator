package com.ezreal.ai.code.generator.domain.user.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserLoginRequest {

    private String userAccount;

    private String userPassword;
}
