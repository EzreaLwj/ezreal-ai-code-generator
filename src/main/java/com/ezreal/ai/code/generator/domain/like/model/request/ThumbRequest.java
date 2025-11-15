package com.ezreal.ai.code.generator.domain.like.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liwenjie.
 * @since 2025-11-15 15:39.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThumbRequest {

    private Long userId;

    private Long blogId;

    private Integer type;
}
