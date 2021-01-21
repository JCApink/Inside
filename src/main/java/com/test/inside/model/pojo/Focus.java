package com.test.inside.model.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Date;
import java.util.Map;


/**
 * <p>
 *
 * </p>
 *
 * @author Bangtidy
 * @since 2020-08-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("focus")
public class Focus implements Serializable {

    private static final long serialVersionUID=1L;

    /**
     * id 用户id
     */
    @TableId(value = "id")
    private Integer id;

    /**
     * todayFocusTime 今天专注时间
     */
    @TableId(value = "todayfocustime")
    private Integer todayfocustime;

    /**
     * totalFocusTime 总专注时间
     */
    @TableId(value = "totalfocustime")
    private Integer totalfocustime;

    @TableId(value = "uptime")
    private String uptime;

    @TableField(exist = false)
    private Map<String,Object> sentence;

}
