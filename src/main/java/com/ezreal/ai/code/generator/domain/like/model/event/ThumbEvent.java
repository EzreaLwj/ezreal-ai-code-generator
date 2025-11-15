package com.ezreal.ai.code.generator.domain.like.model.event;

import cn.hutool.core.util.RandomUtil;
import com.ezreal.ai.code.generator.common.BaseEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author liwenjie.
 * @since 2025-11-16 23:13.
 */
@Component
public class ThumbEvent extends BaseEvent<ThumbEvent.ThumbEventMessage> {

    @Value("${kafka.like.topic.name}")
    private String topic;

    @Override
    public EventMessage<ThumbEvent.ThumbEventMessage> buildEventMessage(ThumbEvent.ThumbEventMessage data) {
        return EventMessage.<ThumbEvent.ThumbEventMessage>builder()
                .id(RandomUtil.randomString(11))
                .timestamp(new Date())
                .data(data)
                .build();
    }

    @Override
    public String topic() {
        return topic;
    }

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ThumbEventMessage {

        /**
         * 用户ID
         */
        private Long userId;
        /**
         * blogID
         */
        private Long blogId;
        /**
         * 事件类型
         */
        private Integer type;
    }
}
