package com.ezreal.ai.code.generator.infrastructrue.repo;

import com.ezreal.ai.code.generator.domain.like.repository.BlogRepository;
import com.ezreal.ai.code.generator.mapper.BlogMapper;
import com.ezreal.ai.code.generator.model.po.Blog;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Repository;

/**
 * @author liwenjie.
 * @since 2025-11-16 11:36.
 */
@Repository
public class BlogRepositoryImpl implements BlogRepository {

    @Resource
    private BlogMapper blogMapper;

    @Override
    public Blog queryBlog(Long id, Long userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(Blog::getId, id);
        queryWrapper.eq(Blog::getUserId, userId);
        return blogMapper.selectOneByQuery(queryWrapper);
    }

    @Override
    public Page<Blog> queryBlogList(int page, int pageSize, Long userId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(Blog::getUserId, userId);
        return blogMapper.paginate(page, pageSize, queryWrapper);
    }
}
