package com.ezreal.ai.code.generator.domain.like.repository;

import com.ezreal.ai.code.generator.domain.like.model.entity.BlogEntity;
import com.ezreal.ai.code.generator.domain.like.model.entity.LikeEntity;

import java.util.List;
import java.util.Map;

/**
 * @author liwenjie.
 * @since 2025-11-15 16:00.
 */
public interface LikeRepository {

    /**
     * 查询blog
     *
     * @param blogId blogId
     * @return BlogEntity
     */
    BlogEntity queryBlog(Long blogId);

    /**
     * 查询点赞记录
     *
     * @param userId userId
     * @param blogId blogId
     * @return LikeEntity
     */
    LikeEntity queryThumbRecord(Long userId, Long blogId);

    /**
     * 保存Redis点赞记录
     *
     * @param userId userId
     * @param blogId blogId
     * @return lua脚本执行结果
     */
    Boolean saveThumbRedisRecord(Long userId, Long blogId);

    /**
     * 保存点赞记录
     *
     * @param userId userId
     * @param blogId blogId
     */
    void saveThumbRecord(Long userId, Long blogId);

    /**
     * 增加点赞数
     *
     * @param blogId blogId
     */
    void increaseThumbCount(Long blogId);

    /**
     * 删除点赞记录
     *
     * @param thumbId thumbId
     */
    void deleteThumbRecord(Long userId, Long blogId, Long thumbId);

    /**
     * 减少点赞数
     *
     * @param blogId blogId
     */
    void descreaseThumbCount(Long blogId);

    /**
     * 是否点赞
     *
     * @param userId userId
     * @param blogId blogId
     * @return boolean
     */
    boolean hasThumb(Long userId, Long blogId);

    /**
     * 批量查询是否点赞
     *
     * @param userId  userId
     * @param blogIds blogIds
     * @return Map<Long, Boolean>
     */
    Map<Long, Boolean> batchHasThumb(Long userId, List<Long> blogIds);

    /**
     * 发送点赞事件
     *
     * @param userId userId
     * @param blogId blogId
     * @param type   type
     */
    void sendThumbEvent(Long userId, Long blogId, Integer type);

    /**
     * 删除Redis点赞记录
     *
     * @param userId userId
     * @param blogId blogId
     * @return 结果
     */
    Boolean deleteThumbRedisRecord(Long userId, Long blogId);
}
