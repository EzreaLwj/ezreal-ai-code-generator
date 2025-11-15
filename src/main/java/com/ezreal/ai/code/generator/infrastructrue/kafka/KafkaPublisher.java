package com.ezreal.ai.code.generator.infrastructrue.kafka;

import com.alibaba.fastjson.JSON;
import com.ezreal.ai.code.generator.common.BaseEvent;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

/**
 * @author liwenjie.
 * @since 2025-11-16 22:06.
 */
@Slf4j
@Component
public class KafkaPublisher {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    public void publish(String topic, BaseEvent.EventMessage<?> eventMessage) {
        try {
            String messageJson = JSON.toJSONString(eventMessage);
            kafkaTemplate.send(topic, messageJson);
        } catch (Exception e) {
            log.error("发送MQ消息失败 topic:{} message:{}", topic, JSON.toJSONString(eventMessage), e);
            throw e;
        }
    }

    public void publish(String topic, String eventMessage) {
        try {
            kafkaTemplate.send(topic, eventMessage);
        } catch (Exception e) {
            log.error("发送MQ消息失败 topic:{} message:{}", topic, JSON.toJSONString(eventMessage), e);
            throw e;
        }
    }
}
