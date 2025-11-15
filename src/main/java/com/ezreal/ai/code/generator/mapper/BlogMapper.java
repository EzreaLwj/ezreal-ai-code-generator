package com.ezreal.ai.code.generator.mapper;

import com.ezreal.ai.code.generator.model.po.Blog;
import com.mybatisflex.core.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 *  映射层。
 *
 * @author <a href="https://github.com/EzreaLwj">程序员Ezreal</a>
 */
@Mapper
public interface BlogMapper extends BaseMapper<Blog> {

    void increaseThumbCount(@Param("blogId") Long blogId);

    void decreaseThumbCount(@Param("blogId") Long blogId);
}
