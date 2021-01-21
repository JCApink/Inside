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
        * @since 2020-08-25
        */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("changes")
public class Changes implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 句子id-主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private int id;

    /**
     * 标题
     */
    @TableField("title")
    private String title;

    /**
     * 标签
     */
    @TableField("tag")
    private String tag;

    /**
     * 用户Id
     */
    @TableField("userid")
    private int userid;


}
