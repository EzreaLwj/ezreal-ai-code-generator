package com.ezreal.ai.code.generator.model.po;

import com.ezreal.ai.code.generator.domain.like.model.entity.LikeEntity;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import java.io.Serializable;
import java.sql.Timestamp;

import java.io.Serial;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *  实体类。
 *
 * @author <a href="https://github.com/EzreaLwj">程序员Ezreal</a>
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table("thumb")
public class Thumb implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Long id;

    @Column("userId")
    private Long userId;

    @Column("blogId")
    private Long blogId;

    @Column("createTime")
    private Timestamp createTime;

    public LikeEntity convert2Entity() {
        return LikeEntity.builder()
                .id(this.id)
                .userId(this.userId)
                .blogId(this.blogId)
                .build();
    }
}
