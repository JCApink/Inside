package com.test.inside.model.pojo;

import com.baomidou.mybatisplus.annotation.TableName;
        import com.baomidou.mybatisplus.annotation.IdType;
        import com.baomidou.mybatisplus.annotation.TableId;
        import com.baomidou.mybatisplus.annotation.TableField;
        import java.io.Serializable;
        import lombok.Data;
        import lombok.EqualsAndHashCode;
        import lombok.experimental.Accessors;

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
@TableName("group_")
public class Group implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * 文章分组id-主键
     */
    @TableId(value = "name", type = IdType.AUTO)
    private Integer name;

    /**
     * 文章分组名称
     */

    @TableField("title")
    private String title;

    /**
     * 用户id
     */
    @TableField("userId")
    private Integer userId;

    public Group() {
    }

    public Group(String title) {
        this.title = title;
    }

    public Group(Integer name, String title) {
        this.name = name;
        this.title = title;
    }
}
