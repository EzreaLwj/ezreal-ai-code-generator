package com.ezreal.ai.code.generator.domain.like.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liwenjie.
 * @since 2025-11-15 16:12.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogEntity {

    private Long id;

    private Long userId;

    private String title;

    private String coverImg;

    private String content;

    private Integer thumbCount;
}
