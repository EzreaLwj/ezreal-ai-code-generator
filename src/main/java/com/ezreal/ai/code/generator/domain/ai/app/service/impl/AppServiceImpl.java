package com.ezreal.ai.code.generator.domain.ai.app.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.ezreal.ai.code.generator.common.PageResponse;
import com.ezreal.ai.code.generator.domain.ai.app.model.*;
import com.ezreal.ai.code.generator.domain.ai.app.model.entity.AppEntity;
import com.ezreal.ai.code.generator.domain.ai.app.repostiory.AppRepository;
import com.ezreal.ai.code.generator.enums.CodeGenTypeEnum;
import com.ezreal.ai.code.generator.enums.DeleteEnum;
import com.ezreal.ai.code.generator.enums.ResponseCode;
import com.ezreal.ai.code.generator.exception.AppException;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.ezreal.ai.code.generator.model.po.App;
import com.ezreal.ai.code.generator.mapper.AppMapper;
import com.ezreal.ai.code.generator.domain.ai.app.service.AppService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 应用 服务层实现。
 *
 * @author <a href="https://github.com/EzreaLwj">程序员Ezreal</a>
 */
@Slf4j
@Service
public class AppServiceImpl extends ServiceImpl<AppMapper, App> implements AppService {

    @Resource
    private AppRepository appRepository;

    @Override
    public boolean addApp(AppAddRequest appAddRequest, Long userId) {
        String codeGenType = appAddRequest.getCodeGenType();
        if (StrUtil.isBlankIfStr(codeGenType)) {
            throw new AppException("codeGenType is blank");
        }
        CodeGenTypeEnum codeGenTypeEnum = CodeGenTypeEnum.getByValue(codeGenType);
        if (codeGenTypeEnum == null) {
            throw new AppException("codeGenType is not support");
        }

        App app = App.builder()
                .appName(appAddRequest.getAppName())
                .cover(appAddRequest.getCover())
                .initPrompt(appAddRequest.getInitPrompt())
                .codeGenType(codeGenType)
                .userId(userId)
                .build();
        app.setDeployKey(RandomUtil.randomString(6));
        return save(app);
    }

    @Override
    public boolean updateApp(AppUpdateRequest appUpdateRequest, Long userId) {
        Long appId = appUpdateRequest.getAppId();
        AppEntity appEntity = appRepository.queryEntity(appId, userId);
        if (appEntity == null) {
            log.error("update request:{}", JSONUtil.toJsonStr(appUpdateRequest));
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), "app is not found");
        }

        if (userId == null || !(userId.equals(appEntity.getUserId()))) {
            log.error("update request:{}", JSONUtil.toJsonStr(appUpdateRequest));
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER);
        }
        App app = App.builder()
                .appName(appUpdateRequest.getAppName())
                .id(appId)
                .build();
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(App::getId, appId);
        queryWrapper.eq(App::getUserId, userId);
        queryWrapper.eq(App::getIsDelete, DeleteEnum.NO.getCode());

        return updateById(app);
    }

    @Override
    public boolean deleteApp(Long id, Long userId) {

        AppEntity appEntity = appRepository.queryEntity(id, userId);
        if (appEntity == null) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), "app is not found");
        }

        // 判断是否是自己的应用
        if (!Objects.equals(appEntity.getUserId(), userId)) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), "app is not belong to you");
        }
        return appRepository.delete(id, userId);
    }

    @Override
    public AppEntity queryAppInfo(Long id, Long userId) {
        AppEntity appEntity = appRepository.queryEntity(id, userId);
        if (appEntity == null) {
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER.getCode(), "app is not found");
        }
        return appEntity;
    }

    @Override
    public PageResponse<AppQueryResponse> pageQuery(AppPageQueryRequest appPageQueryRequest, Long userId) {
        Page<AppEntity> page = appRepository.pageQuery(appPageQueryRequest, userId);
        PageResponse<AppQueryResponse> pageResponse = new PageResponse<>();
        pageResponse.setTotal(page.getTotalPage());
        List<AppEntity> records = page.getRecords();
        pageResponse.setList(convert2Response(records));
        return pageResponse;
    }

    private List<AppQueryResponse> convert2Response(List<AppEntity> records) {
        return records.stream().map(this::convert2Response).collect(Collectors.toList());
    }

    private AppQueryResponse convert2Response(AppEntity appEntity) {
        return AppQueryResponse.builder()
                .id(appEntity.getId())
                .appName(appEntity.getAppName())
                .cover(appEntity.getCover())
                .initPrompt(appEntity.getInitPrompt())
                .codeGenType(appEntity.getCodeGenType())
                .deployKey(appEntity.getDeployKey())
                .deployedTime(appEntity.getDeployedTime())
                .priority(appEntity.getPriority())
                .userId(appEntity.getUserId())
                .editTime(appEntity.getEditTime())
                .createTime(appEntity.getCreateTime())
                .updateTime(appEntity.getUpdateTime())
                .build();
    }
}
