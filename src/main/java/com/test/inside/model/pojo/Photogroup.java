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
 * @since 2020-07-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("photogroup")
public class Photogroup implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 文章分组id-主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 文章分组名称
     */

    @TableField("listtitle")
    private String listtitle;

    /**
     * 用户id
     */
    @TableField("userId")
    private Integer userId;

    /**
     * 列表id
     */
    @TableField("listid")
   private Integer listid;
}
