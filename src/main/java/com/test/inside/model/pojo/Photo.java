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
@TableName("photo")
public class Photo implements Serializable {

    private static final long serialVersionUID=1L;
    //图片id
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    //用户id
    private Integer userid;

    //图片的list 编号
    private int listid;

    //list的名称
    @TableField(exist = false)
    private String listtitle;

    //图片的相关信息
    private String title;//图片名称
    private String description;//图片的描述
    private String url;//图片的url
    private String name;//上传的图片名称


}
