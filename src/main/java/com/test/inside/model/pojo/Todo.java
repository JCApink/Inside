package com.test.inside.model.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * <p>
 *
 * </p>
 *
 * @author Bangtidy
 * @since 2020-06-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("todo")
public class Todo implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id
     */

    private Integer userid;

    private Integer id;
    /**
     * 内容
     */
    private String content;


    @JSONField(format ="yyyy-MM-dd HH:mm:ss")
    private Timestamp adddate;

    @JSONField(format ="yyyy-MM-dd HH:mm:ss")
    private Timestamp finishdate;

    private boolean completed;

}
