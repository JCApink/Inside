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
@TableName("calendar")
public class Calendar implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id
     */

    private Integer userid;

    /**
     * 标题
     */
    private String emoji;

    /**
     * 内容
     */
    private String content;

    private Integer id;

    /**
     * 日期
     */
    @JSONField(format ="yyyy-MM-dd HH:mm:ss" )
    private Timestamp date;

}
