package com.ezreal.ai.code.generator.controller.consumer;

import com.ezreal.ai.code.generator.domain.like.service.LikeService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * @author liwenjie.
 * @since 2025-11-16 22:48.
 */
@Slf4j
@Component
public class ThumbEventConsumer {

    @Resource
    private LikeService likeService;

    @KafkaListener(topics = "${kafka.like.topic.name}", groupId = "${kafka.credit.topic.user}", concurrency = "1")
    public void consume(String message) {
        log.info("消费点赞事件消息:{}", message);
    }
}
