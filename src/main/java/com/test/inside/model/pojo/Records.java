package com.test.inside.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;


import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Map;

/**
 * @author Bangtidy
 * @since 2020-08-02
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("records_")
public class Records implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * 博客唯一id-主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;


    /**
     * 博客标题
     */
    @NotBlank(message = "标题不能为空")
    @TableField("title")
    private String title;

    /**
     * 博客内容
     */
    @NotBlank(message = "内容不能为空")
    private String content;

    /**
     * 博客发布时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss")
    @TableField("updateTime")
    private LocalDateTime updateTime;

    /**
     * 星标
     */
    @TableField("isStar")
    private Boolean isStar;

    /**
     * 博客分类
     */
    @TableField("groupId")
    private Integer groupId;

    /**
     * 文章类别
     */
    @TableField(exist = false)
    private String group;

    /**
     * 图片url
     */
    @TableField("url")
    private String url;

    /**
     * 博主id
     */
    @TableId(value = "user_id")
    private Integer user_id;


    public Records() {
    }

    public Records(@NotBlank(message = "标题不能为空") String title, @NotBlank(message = "内容不能为空") String content, Boolean isStar, Integer groupId, String url) {
        this.title = title;
        this.content = content;
        this.isStar = isStar;
        this.groupId = groupId;
        this.url = url;
    }

    public Records(Integer id, @NotBlank(message = "标题不能为空") String title, @NotBlank(message = "内容不能为空") String content, Boolean isStar, Integer groupId, String url) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.isStar = isStar;
        this.groupId = groupId;
        this.url = url;
    }

    public Records(Integer id, @NotBlank(message = "标题不能为空") String title, @NotBlank(message = "内容不能为空") String content, LocalDateTime updateTime, Boolean isStar, Integer groupId, String url, Integer user_id) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.updateTime = updateTime;
        this.isStar = isStar;
        this.groupId = groupId;
        this.url = url;
        this.user_id = user_id;
    }

    public Records(Integer id, @NotBlank(message = "标题不能为空") String title, @NotBlank(message = "内容不能为空") String content, LocalDateTime updateTime, Boolean isStar, String group, String url) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.updateTime = updateTime;
        this.isStar = isStar;
        this.group = group;
        this.url = url;
    }

    public Records(Integer id, @NotBlank(message = "标题不能为空") String title, @NotBlank(message = "内容不能为空") String content, LocalDateTime updateTime, Boolean isStar, Integer groupId, String group, String url, Integer user_id) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.updateTime = updateTime;
        this.isStar = isStar;
        this.groupId = groupId;
        this.group = group;
        this.url = url;
        this.user_id = user_id;
    }
}
