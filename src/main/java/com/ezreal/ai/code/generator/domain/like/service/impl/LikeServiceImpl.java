package com.ezreal.ai.code.generator.domain.like.service.impl;

import com.ezreal.ai.code.generator.domain.like.model.entity.BlogEntity;
import com.ezreal.ai.code.generator.domain.like.model.request.ThumbRequest;
import com.ezreal.ai.code.generator.domain.like.model.valobj.LikeActionEnum;
import com.ezreal.ai.code.generator.domain.like.repository.LikeRepository;
import com.ezreal.ai.code.generator.domain.like.service.LikeService;
import com.ezreal.ai.code.generator.enums.ResponseCode;
import com.ezreal.ai.code.generator.exception.AppException;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author liwenjie.
 * @since 2025-11-15 15:23.
 */
@Slf4j
@Service
public class LikeServiceImpl implements LikeService {

    @Resource
    private LikeRepository likeRepository;

    @Override
    public Boolean thumb(ThumbRequest request) {

        Long blogId = request.getBlogId();
        Long userId = request.getUserId();
        Integer type = request.getType();
        LikeActionEnum likeActionEnum = LikeActionEnum.of(type);
        if (LikeActionEnum.UNKNOWN.equals(likeActionEnum)) {
            log.error("likeActionEnum:{} 不合法", type);
            throw new AppException(ResponseCode.ILLEGAL_PARAMETER);
        }

        BlogEntity blogEntity = likeRepository.queryBlog(blogId);
        if (blogEntity == null) {
            log.error("blogId:{} 不存在", blogId);
            throw new AppException(ResponseCode.BLOG_NO_FOUND);
        }

        if (likeActionEnum.equals(LikeActionEnum.LIKE)) {
            if (!likeRepository.saveThumbRedisRecord(userId, blogId)) {
                log.error("点赞失败，userId：{},blogId:{}", userId, blogId);
                return false;
            }
            likeRepository.sendThumbEvent(userId, blogId, type);
            return true;
        }
        if (likeActionEnum.equals(LikeActionEnum.CANCEL_LIKE)) {
            if (!likeRepository.deleteThumbRedisRecord(userId, blogId)) {
                log.error("取消点赞失败，userId：{},blogId:{}", userId, blogId);
                return false;
            }
            likeRepository.sendThumbEvent(userId, blogId, type);
            return true;
        }
        return false;
    }
}
