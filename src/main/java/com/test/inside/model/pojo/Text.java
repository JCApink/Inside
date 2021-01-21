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
        * @since 2020-08-12
        */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("text")
public class Text implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 句子id-主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    /**
     * 句子内容
     */
    @TableField("content")
    private String content;

    /**
     * 用户Id
     */
    @TableField("userid")
    private int userid;

    public Text() {
    }

    public Text(String content) {
        this.content = content;
    }


}
