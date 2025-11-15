package com.ezreal.ai.code.generator.domain.like.model.entity;

import lombok.Builder;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author liwenjie.
 * @since 2025-11-15 16:20.
 */
@Data
@Builder
public class LikeEntity {

    private Long id;

    private Long userId;

    private Long blogId;

    private Timestamp createTime;
}
