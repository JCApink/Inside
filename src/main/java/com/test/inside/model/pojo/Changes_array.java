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
        * @author Bangtidy
        * @since 2020-08-25
        */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Changes_array implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 标题
     */
    private String title;

    /**
     * 标签
     */
    private String[] tags;


    public Changes_array(String title, String[] tags) {
        this.title = title;
        this.tags = tags;
    }

    public Changes_array(String title) {
        this.title = title;
    }

    public Changes_array() {
    }
}
