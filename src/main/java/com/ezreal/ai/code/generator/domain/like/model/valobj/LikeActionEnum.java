package com.ezreal.ai.code.generator.domain.like.model.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author liwenjie.
 * @since 2025-11-15 16:29.
 */
@Getter
@AllArgsConstructor
public enum LikeActionEnum {

    UNKNOWN(-1, "未知"),
    LIKE(0, "点赞"),
    CANCEL_LIKE(1, "取消点赞");

    private final int code;
    private final String desc;

    public static LikeActionEnum of(int code) {
        for (LikeActionEnum likeActionEnum : values()) {
            if (likeActionEnum.code == code) {
                return likeActionEnum;
            }
        }
        return UNKNOWN;
    }
}
