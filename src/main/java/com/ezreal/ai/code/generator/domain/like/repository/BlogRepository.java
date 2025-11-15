package com.ezreal.ai.code.generator.domain.like.repository;

import com.ezreal.ai.code.generator.model.po.Blog;
import com.mybatisflex.core.paginate.Page;

/**
 * @author liwenjie.
 * @since 2025-11-16 10:58.
 */
public interface BlogRepository {

    Blog queryBlog(Long id, Long userId);

    Page<Blog> queryBlogList(int page, int pageSize, Long userId);

}
