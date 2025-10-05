package com.ezreal.ai.code.generator.domain.ai.app.service;

import com.ezreal.ai.code.generator.common.PageResponse;
import com.ezreal.ai.code.generator.domain.ai.app.model.AppAddRequest;
import com.ezreal.ai.code.generator.domain.ai.app.model.AppPageQueryRequest;
import com.ezreal.ai.code.generator.domain.ai.app.model.AppQueryResponse;
import com.ezreal.ai.code.generator.domain.ai.app.model.AppUpdateRequest;
import com.ezreal.ai.code.generator.domain.ai.app.model.entity.AppEntity;
import com.mybatisflex.core.service.IService;
import com.ezreal.ai.code.generator.model.po.App;

/**
 * 应用 服务层。
 *
 * @author <a href="https://github.com/EzreaLwj">程序员Ezreal</a>
 */
public interface AppService extends IService<App> {

    boolean addApp(AppAddRequest appAddRequest, Long userId);

    boolean updateApp(AppUpdateRequest appUpdateRequest, Long userId);

    boolean deleteApp(Long id, Long userId);

    AppEntity queryAppInfo(Long id, Long userId);

    PageResponse<AppQueryResponse> pageQuery(AppPageQueryRequest appPageQueryRequest, Long id);
}
