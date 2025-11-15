package com.ezreal.ai.code.generator.model.po;

import com.ezreal.ai.code.generator.domain.like.model.entity.BlogEntity;
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
@Table("blog")
public class Blog implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id(keyType = KeyType.Auto)
    private Long id;

    @Column("userId")
    private Long userId;

    /**
     * 标题
     */
    @Column("title")
    private String title;

    /**
     * 封面
     */
    @Column("coverImg")
    private String coverImg;

    /**
     * 内容
     */
    @Column("content")
    private String content;

    /**
     * 点赞数
     */
    @Column("thumbCount")
    private Integer thumbCount;

    /**
     * 创建时间
     */
    @Column("createTime")
    private Timestamp createTime;

    /**
     * 更新时间
     */
    @Column("updateTime")
    private Timestamp updateTime;

    public BlogEntity convert2Entity() {
        return BlogEntity.builder()
                .id(this.id)
                .userId(this.userId)
                .title(this.title)
                .coverImg(this.coverImg)
                .content(this.content)
                .thumbCount(this.thumbCount)
                .build();
    }
}
