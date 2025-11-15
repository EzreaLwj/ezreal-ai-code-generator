package com.ezreal.ai.code.generator.domain.like.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author liwenjie.
 * @since 2025-11-16 10:56.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BlogVo {

    private Long id;

    private Long userId;

    private String title;

    private String coverImg;

    private String content;

    private Integer thumbCount;

    private boolean isThumb;
}
