package com.ezreal.ai.code.generator.infrastructrue.repo;

import cn.hutool.core.collection.ListUtil;
import com.ezreal.ai.code.generator.common.BaseEvent;
import com.ezreal.ai.code.generator.common.RedisConstants;
import com.ezreal.ai.code.generator.domain.like.model.entity.BlogEntity;
import com.ezreal.ai.code.generator.domain.like.model.entity.LikeEntity;
import com.ezreal.ai.code.generator.domain.like.model.event.ThumbEvent;
import com.ezreal.ai.code.generator.domain.like.repository.LikeRepository;
import com.ezreal.ai.code.generator.infrastructrue.kafka.KafkaPublisher;
import com.ezreal.ai.code.generator.infrastructrue.redis.IRedisService;
import com.ezreal.ai.code.generator.mapper.BlogMapper;
import com.ezreal.ai.code.generator.mapper.ThumbMapper;
import com.ezreal.ai.code.generator.model.po.Blog;
import com.ezreal.ai.code.generator.model.po.Thumb;
import com.mybatisflex.core.query.QueryWrapper;
import jakarta.annotation.Resource;
import org.redisson.api.RMap;
import org.redisson.api.RScript;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class LikeRepositoryImpl implements LikeRepository {

    @Resource
    private BlogMapper blogMapper;
    @Resource
    private ThumbMapper thumbMapper;
    @Resource
    private IRedisService redisService;
    @Resource
    private KafkaPublisher kafkaPublisher;
    @Resource
    private ThumbEvent thumbEvent;

    public static final String THUMB_SCRIPT = """  
                local userThumbKey = KEYS[1]  
                local blogId = ARGV[1]  
          
                -- 判断是否已经点赞  
                if redis.call("HEXISTS", userThumbKey, blogId) == 1 then  
                    return -1  
                end  
          
                -- 添加点赞记录  
                redis.call("HSET", userThumbKey, blogId, 1)  
                return 1  
        """;

    public static final String UN_THUMB_SCRIPT = """  
        local userThumbKey = KEYS[1]  
        local blogId = ARGV[1]  
          
        -- 判断是否已点赞  
        if redis.call("HEXISTS", userThumbKey, blogId) == 0 then  
            return -1  
        end  
          
        -- 删除点赞记录  
        redis.call("HDEL", userThumbKey, blogId)  
        return 1  
        """;

    @Override
    public BlogEntity queryBlog(Long blogId) {

        Blog blog = blogMapper.selectOneById(blogId);
        if (blog == null) {
            return null;
        }
        return blog.convert2Entity();
    }

    @Override
    public LikeEntity queryThumbRecord(Long userId, Long blogId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(Thumb::getUserId, userId);
        queryWrapper.eq(Thumb::getBlogId, blogId);
        Thumb thumb = thumbMapper.selectOneByQuery(queryWrapper);
        if (thumb == null) {
            return null;
        }
        return thumb.convert2Entity();
    }

    @Override
    public Boolean saveThumbRedisRecord(Long userId, Long blogId) {
        RScript script = redisService.getScript();
        Integer result = script.eval(RScript.Mode.READ_WRITE,
                THUMB_SCRIPT,
                RScript.ReturnType.INTEGER,
                ListUtil.of(buildUserLikeKey(userId)),
                blogId);
        return result == 1;
    }

    @Override
    public Boolean deleteThumbRedisRecord(Long userId, Long blogId) {
        RScript script = redisService.getScript();
        Integer result = script.eval(RScript.Mode.READ_WRITE,
                UN_THUMB_SCRIPT,
                RScript.ReturnType.INTEGER,
                ListUtil.of(buildUserLikeKey(userId)),
                blogId);
        return result == 1;
    }

    @Override
    public void saveThumbRecord(Long userId, Long blogId) {
        Thumb thumb = Thumb.builder()
                .userId(userId)
                .blogId(blogId)
                .createTime(new Timestamp(System.currentTimeMillis()))
                .build();
    }

    @Override
    public void increaseThumbCount(Long blogId) {
        blogMapper.increaseThumbCount(blogId);
    }

    @Override
    public void deleteThumbRecord(Long userId, Long blogId, Long thumbId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq(Thumb::getId, thumbId);
        thumbMapper.deleteByQuery(queryWrapper);
    }

    @Override
    public void descreaseThumbCount(Long blogId) {
        blogMapper.decreaseThumbCount(blogId);
    }

    @Override
    public boolean hasThumb(Long userId, Long blogId) {
        RMap<Object, Object> map = redisService.getMap(buildUserLikeKey(userId));
        return map.containsKey(blogId.toString());
    }

    public Map<Long, Boolean> batchHasThumb(Long userId, List<Long> blogIds) {
        RMap<Object, Object> map = redisService.getMap(buildUserLikeKey(userId));
        Map<Long, Boolean> result = new HashMap<>();
        for (Long blogId : blogIds) {
            result.put(blogId, map.containsKey(blogId.toString()));
        }
        return result;
    }

    @Override
    public void sendThumbEvent(Long userId, Long blogId, Integer type) {
        BaseEvent.EventMessage<ThumbEvent.ThumbEventMessage> thumbEventMessageEventMessage =
                thumbEvent.buildEventMessage(ThumbEvent.ThumbEventMessage.builder()
                .userId(userId)
                .blogId(blogId)
                .type(type)
                .build());
        kafkaPublisher.publish(thumbEvent.topic(), thumbEventMessageEventMessage);
    }

    private String buildUserLikeKey(Long userId) {
        return RedisConstants.BLOG_LIKE_USER + userId;
    }

    private BlogEntity convert2Entity(Blog blog) {
        return BlogEntity.builder()
                .id(blog.getId())
                .userId(blog.getUserId())
                .title(blog.getTitle())
                .coverImg(blog.getCoverImg())
                .content(blog.getContent())
                .thumbCount(blog.getThumbCount())
                .build();
    }
}
