package com.ezreal.ai.code.generator.domain.like.service;

import com.ezreal.ai.code.generator.domain.like.model.request.ThumbRequest;

/**
 * @author liwenjie.
 * @since 2025-11-15 15:23.
 */
public interface LikeService {

    Boolean thumb(ThumbRequest request);
}
