package com.ezreal.ai.code.generator.domain.like.service;

import com.ezreal.ai.code.generator.common.Response;
import com.ezreal.ai.code.generator.domain.like.model.request.BlogQueryRequest;
import com.ezreal.ai.code.generator.domain.like.model.response.BlogVo;
import com.ezreal.ai.code.generator.model.po.Blog;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.service.IService;

import java.util.List;

/**
 *  服务层。
 *
 * @author <a href="https://github.com/EzreaLwj">程序员Ezreal</a>
 */
public interface BlogService extends IService<Blog> {

    /**
     * 查询博客列表
     * @return 博客列表
     */
    Response<Page<BlogVo>> queryBlogList(BlogQueryRequest request, Long userId);

    /**
     * 查询博客列表
     * @param blogId
     * @param userId
     * @return
     */
    Response<BlogVo> queryBlog(Long blogId, Long userId);
}
