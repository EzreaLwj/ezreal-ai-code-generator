package com.ezreal.ai.code.generator.controller;

import com.ezreal.ai.code.generator.common.Response;
import com.ezreal.ai.code.generator.domain.like.model.request.BlogQueryRequest;
import com.ezreal.ai.code.generator.domain.like.model.response.BlogVo;
import com.ezreal.ai.code.generator.domain.user.model.entity.UserLoginEntity;
import com.ezreal.ai.code.generator.domain.user.service.UserService;
import com.ezreal.ai.code.generator.model.po.Blog;
import com.mybatisflex.core.paginate.Page;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.beans.factory.annotation.Autowired;
import com.ezreal.ai.code.generator.domain.like.service.BlogService;
import org.springframework.web.bind.annotation.RestController;

/**
 *  控制层。
 *
 * @author <a href="https://github.com/EzreaLwj">程序员Ezreal</a>
 */
@RestController
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private UserService userService;

    /**
     * 保存。
     *
     * @param blog 
     * @return {@code true} 保存成功，{@code false} 保存失败
     */
    @PostMapping("save")
    public boolean save(@RequestBody Blog blog) {
        return blogService.save(blog);
    }

    /**
     * 根据主键删除。
     *
     * @param id 主键
     * @return {@code true} 删除成功，{@code false} 删除失败
     */
    @DeleteMapping("remove/{id}")
    public boolean remove(@PathVariable Long id) {
        return blogService.removeById(id);
    }

    /**
     * 根据主键更新。
     *
     * @param blog 
     * @return {@code true} 更新成功，{@code false} 更新失败
     */
    @PutMapping("update")
    public boolean update(@RequestBody Blog blog) {
        return blogService.updateById(blog);
    }

    /**
     * 查询所有。
     *
     * @return 所有数据
     */
    @GetMapping("list")
    public Response<Page<BlogVo>> list(BlogQueryRequest request, HttpServletRequest httpServletRequest) {
        UserLoginEntity loginUser = userService.getLoginUser(httpServletRequest);
        return blogService.queryBlogList(request, loginUser.getId());
    }

    /**
     * 根据主键获取。
     *
     * @param id 主键
     * @return 详情
     */
    @GetMapping("getInfo/{id}")
    public Response<BlogVo> getInfo(@PathVariable Long id, HttpServletRequest httpServletRequest) {
        UserLoginEntity loginUser = userService.getLoginUser(httpServletRequest);
        return blogService.queryBlog(id, loginUser.getId());
    }

    /**
     * 分页查询。
     *
     * @param page 分页对象
     * @return 分页对象
     */
    @GetMapping("page")
    public Page<Blog> page(Page<Blog> page) {
        return blogService.page(page);
    }

}
