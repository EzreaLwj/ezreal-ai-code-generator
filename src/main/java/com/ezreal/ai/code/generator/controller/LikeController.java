package com.ezreal.ai.code.generator.controller;

import com.ezreal.ai.code.generator.common.Response;
import com.ezreal.ai.code.generator.domain.like.model.request.ThumbRequest;
import com.ezreal.ai.code.generator.domain.like.service.LikeService;
import com.ezreal.ai.code.generator.utils.ResultUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author liwenjie.
 * @since 2025-11-15 15:16.
 */
@Tag(name = "点赞")
@RestController
@RequestMapping("/like")
public class LikeController {

    @Resource
    private LikeService likeService;

    @PostMapping("/thumb")
    public Response<Boolean> thumb(@RequestBody ThumbRequest thumbRequest,
                                   HttpServletRequest request) {
        Boolean thumb = likeService.thumb(thumbRequest);
        return ResultUtils.success(thumb);
    }
}
