package com.ezreal.ai.code.generator.domain.like.service.impl;

import com.ezreal.ai.code.generator.common.Response;
import com.ezreal.ai.code.generator.domain.like.model.entity.LikeEntity;
import com.ezreal.ai.code.generator.domain.like.model.request.BlogQueryRequest;
import com.ezreal.ai.code.generator.domain.like.model.response.BlogVo;
import com.ezreal.ai.code.generator.domain.like.repository.BlogRepository;
import com.ezreal.ai.code.generator.domain.like.repository.LikeRepository;
import com.ezreal.ai.code.generator.domain.like.service.BlogService;
import com.ezreal.ai.code.generator.enums.ResponseCode;
import com.ezreal.ai.code.generator.exception.AppException;
import com.ezreal.ai.code.generator.mapper.BlogMapper;
import com.ezreal.ai.code.generator.model.po.Blog;
import com.ezreal.ai.code.generator.utils.ResultUtils;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 *  服务层实现。
 *
 * @author <a href="https://github.com/EzreaLwj">程序员Ezreal</a>
 */
@Slf4j
@Service
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog>  implements BlogService{

    @Resource
    private BlogRepository blogRepository;
    @Resource
    private LikeRepository likeRepository;

    @Override
    public Response<BlogVo> queryBlog(Long blogId, Long userId) {
        Blog blog = blogRepository.queryBlog(blogId, userId);
        if (blog == null) {
            log.error("博客不存在，blogId={}, userId={}", blogId, userId);
            throw new AppException(ResponseCode.BLOG_NO_FOUND);
        }
        BlogVo blogVo = new BlogVo();
        blogVo.setId(blog.getId());
        blogVo.setContent(blog.getContent());
        blogVo.setTitle(blog.getTitle());
        blogVo.setCoverImg(blog.getCoverImg());
        blogVo.setThumbCount(blog.getThumbCount());
        blogVo.setUserId(blog.getUserId());
        blogVo.setThumb(likeRepository.hasThumb(userId, blogId));
        return ResultUtils.success(blogVo);
    }

    @Override
    public Response<Page<BlogVo>> queryBlogList(BlogQueryRequest request, Long userId) {
        Page<Blog> blogPage = blogRepository.queryBlogList(request.getPage(), request.getPageSize(), userId);

        Page<BlogVo> page = new Page<>();
        List<Blog> records = blogPage.getRecords();

        List<Long> blogIds = records.stream().map(Blog::getId).toList();
        Map<Long, Boolean> hasThumbMap = likeRepository.batchHasThumb(userId, blogIds);
        List<BlogVo> blogVos = records.stream().map(blog -> {
            BlogVo blogVo = new BlogVo();
            Long blogId = blog.getId();
            blogVo.setId(blogId);
            blogVo.setContent(blog.getContent());
            blogVo.setTitle(blog.getTitle());
            blogVo.setCoverImg(blog.getCoverImg());
            blogVo.setThumbCount(blog.getThumbCount());
            blogVo.setUserId(blog.getUserId());
            blogVo.setThumb(hasThumbMap.get(blogId));
            return blogVo;
        }).toList();

        page.setRecords(blogVos);
        page.setTotalPage(blogPage.getTotalPage());
        return ResultUtils.success(page);
    }
}
