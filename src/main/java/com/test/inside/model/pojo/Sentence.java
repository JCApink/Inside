package com.test.inside.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
        * <p>
        *
        * </p>
        *
        * @author Bangtidy
        * @since 2020-07-27
        */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sentence")
public class Sentence implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 句子id-主键
     */
    @TableId(value = "key", type = IdType.AUTO)
    private int key;

    /**
     * 句子内容
     */
    @TableField("value")
    private String value;

    /**
     * 用户Id
     */
    private int userId;

    public Sentence() {
    }

    public Sentence(String value) {
        this.value = value;
    }
}
