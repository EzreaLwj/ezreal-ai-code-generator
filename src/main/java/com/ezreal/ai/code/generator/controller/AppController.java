package com.ezreal.ai.code.generator.controller;

import com.ezreal.ai.code.generator.common.PageResponse;
import com.ezreal.ai.code.generator.common.Response;
import com.ezreal.ai.code.generator.domain.ai.app.model.*;
import com.ezreal.ai.code.generator.domain.ai.app.model.entity.AppEntity;
import com.ezreal.ai.code.generator.domain.user.model.entity.UserLoginEntity;
import com.ezreal.ai.code.generator.domain.user.service.UserService;
import com.ezreal.ai.code.generator.model.po.User;
import com.ezreal.ai.code.generator.utils.ResultUtils;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.ezreal.ai.code.generator.model.po.App;
import com.ezreal.ai.code.generator.domain.ai.app.service.AppService;

import java.util.List;

/**
 * 应用 控制层。
 *
 * @author <a href="https://github.com/EzreaLwj">程序员Ezreal</a>
 */
@Tag(name = "应用")
@RestController
@RequestMapping("/app")
public class AppController {

    @Autowired
    private AppService appService;
    @Autowired
    private UserService userService;

    /**
     * 根据主键删除应用。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    @Operation(summary = "根据主键删除应用")
    public boolean remove(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        UserLoginEntity loginUser = userService.getLoginUser(httpServletRequest);
        return appService.deleteApp(id, loginUser.getId());
    }

    /**
     * 保存应用。
     *
     * @param app 应用
     * @return {@code true} 保存成功，{@code false} 保存失败
     */
    @PostMapping("save")
    @Operation(summary = "保存应用")
    public boolean save(@RequestBody AppAddRequest app, HttpServletRequest httpServletRequest) {
        UserLoginEntity user = userService.getLoginUser(httpServletRequest);
        return appService.addApp(app, user.getId());
    }

    /**
     * 根据主键更新应用。
     *
     * @param request 应用
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    @Operation(summary = "根据主键更新应用")
    public boolean update(@RequestBody AppUpdateRequest request, HttpServletRequest httpServletRequest) {
        UserLoginEntity user = userService.getLoginUser(httpServletRequest);
        return appService.updateApp(request, user.getId());
    }

    private AppQueryResponse convert2Response(AppEntity appEntity) {
        AppQueryResponse appQueryResponse = AppQueryResponse.builder()
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
        return appQueryResponse;
    }

    /**
     * 根据主键获取应用。
     *
     * @param id 应用主键
     * @return 应用详情
     */
    @GetMapping("getInfo/{id}")
    @Operation(summary = "根据主键获取应用")
    public Response<AppQueryResponse> getInfo(@PathVariable Long id, HttpServletRequest servletRequest) {
        UserLoginEntity loginUser = userService.getLoginUser(servletRequest);
        AppEntity appEntity = appService.queryAppInfo(id, loginUser.getId());
        return ResultUtils.success(convert2Response(appEntity));
    }

    /**
     * 分页查询应用。
     *
     * @param appPageQueryRequest 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    @Operation(summary = "分页查询应用")
    public Response<PageResponse<AppQueryResponse>> page(AppPageQueryRequest appPageQueryRequest, HttpServletRequest httpServletRequest) {
        UserLoginEntity user = userService.getLoginUser(httpServletRequest);
        PageResponse<AppQueryResponse> appPageQueryResponse = appService.pageQuery(appPageQueryRequest, user.getId());
        return ResultUtils.success(appPageQueryResponse);
    }

}
