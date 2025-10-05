package com.ezreal.ai.code.generator.domain.ai.app.repostiory;

import com.ezreal.ai.code.generator.domain.ai.app.model.AppPageQueryRequest;
import com.ezreal.ai.code.generator.domain.ai.app.model.entity.AppEntity;
import com.mybatisflex.core.paginate.Page;

public interface AppRepository {

    AppEntity queryEntity(Long id, Long userId);

    boolean delete(Long id, Long userId);

    Page<AppEntity> pageQuery(AppPageQueryRequest appPageQueryRequest, Long userId);
}
