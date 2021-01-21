package com.test.inside.model.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("mp3")
public class MP3Info implements Serializable {

    private static final long serialVersionUID=1L;
    //id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 歌名
     */
    @TableField("title")
    private String title;

    /**
     * 用户id
     */
    @TableField("userid")
    private Integer userid;


    /**
     * 歌手
     */
    @TableField(exist = false)
    private String artist;

    /**
     * 照片
     */
    @TableField(exist = false)
    private String pic;

    /**
     * url
     */
    @TableField(exist = false)
    private String src;

}
