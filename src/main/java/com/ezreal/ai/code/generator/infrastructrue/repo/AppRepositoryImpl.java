package com.ezreal.ai.code.generator.infrastructrue.repo;


import com.ezreal.ai.code.generator.domain.ai.app.model.AppPageQueryRequest;
import com.ezreal.ai.code.generator.domain.ai.app.model.entity.AppEntity;
import com.ezreal.ai.code.generator.domain.ai.app.repostiory.AppRepository;
import com.ezreal.ai.code.generator.enums.DeleteEnum;
import com.ezreal.ai.code.generator.mapper.AppMapper;
import com.ezreal.ai.code.generator.model.po.App;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AppRepositoryImpl implements AppRepository {

    @Resource
    private AppMapper appMapper;

    @Override
    public AppEntity queryEntity(Long id, Long userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(App::getId, id);
        queryWrapper.eq(App::getUserId, userId);
        queryWrapper.eq(App::getIsDelete, DeleteEnum.NO.getCode());
        App app = appMapper.selectOneByQuery(queryWrapper);
        if (app == null) {
            return null;
        }
        return AppEntity.builder()
                .id(app.getId())
                .appName(app.getAppName())
                .cover(app.getCover())
                .initPrompt(app.getInitPrompt())
                .codeGenType(app.getCodeGenType())
                .deployKey(app.getDeployKey())
                .deployedTime(app.getDeployedTime())
                .priority(app.getPriority())
                .userId(app.getUserId())
                .editTime(app.getEditTime())
                .createTime(app.getCreateTime())
                .updateTime(app.getUpdateTime())
                .build();
    }

    @Override
    public boolean delete(Long id, Long userId) {

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(App::getId, id);
        queryWrapper.eq(App::getUserId, userId);
        queryWrapper.eq(App::getIsDelete, DeleteEnum.NO.getCode());

        App app = new App();
        app.setIsDelete(DeleteEnum.YES.getCode());
        int result = appMapper.updateByQuery(app, queryWrapper);
        return result > 0;
    }

    @Override
    public Page<AppEntity> pageQuery(AppPageQueryRequest appPageQueryRequest, Long userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(App::getUserId, userId);
        queryWrapper.eq(App::getIsDelete, DeleteEnum.NO.getCode());
        Page<App> appPage = new Page<>();
        appPage.setPageNumber(appPageQueryRequest.getPage());
        appPage.setPageSize(appPageQueryRequest.getPageSize());
        appPage = appMapper.paginate(appPage, queryWrapper);
        Page<AppEntity> appEntityPage = new Page<>();
        appEntityPage.setPageNumber(appPage.getPageNumber());
        appEntityPage.setPageSize(appPage.getPageSize());
        appEntityPage.setTotalPage(appPage.getTotalPage());
        appEntityPage.setRecords(convert2Entity(appPage.getRecords()));
        return appEntityPage;
    }

    private List<AppEntity> convert2Entity(List<App> appList) {
        return appList.stream().map(app -> AppEntity.builder()
                .id(app.getId())
                .appName(app.getAppName())
                .cover(app.getCover())
                .initPrompt(app.getInitPrompt())
                .codeGenType(app.getCodeGenType())
                .deployKey(app.getDeployKey())
                .deployedTime(app.getDeployedTime())
                .priority(app.getPriority())
                .userId(app.getUserId())
                .editTime(app.getEditTime())
                .createTime(app.getCreateTime())
                .updateTime(app.getUpdateTime())
                .build()).collect(Collectors.toList());
    }

}
