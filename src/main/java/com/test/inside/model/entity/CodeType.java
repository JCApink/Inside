package com.test.inside.model.entity;



public enum CodeType {


     //状态码
    SUCCESS_STATUS(0, "成功"),

    USER_NOT_LOGIN(101, "用户未登录"),
    PERMISSION_VERIFY_FAIL(102, "权限验证失败"),
    SERVER_EXCEPTION(103, "服务器异常"),

    DELETE_ARTICLE_FAIL(201, "删除文章失败"),
    ARTICLE_HAS_THUMBS_UP(203, "文章已经点过赞了"),
    ARTICLE_NOT_EXIST(204, "文章不存在"),
    PUBLISH_ARTICLE_NO_PERMISSION(205, "发表文章没有权限"),
    PUBLISH_ARTICLE_EXCEPTION(206, "服务器异常"),

    FIND_TAGS_CLOUD(301, "获得所有标签成功"),
    FIND_ARTICLE_BY_TAG(302, "通过标签获得文章成功"),

    ADD_CATEGORY_SUCCESS(401, "添加分类成功"),
    CATEGORY_EXIST(402, "分类已存在"),
    DELETE_CATEGORY_SUCCESS(403, "删除分类成功"),
    CATEGORY_NOT_EXIST(404, "分类不存在"),
    CATEGORY_HAS_ARTICLE(405, "分类下存在文章，删除失败"),


    USERNAME_TOO_LANG(501, "用户名太长"),
    USERNAME_BLANK(502, "用户名为空"),
    HAS_MODIFY_USERNAME(503, "修改个人信息成功，并且修改了用户名"),
    NOT_MODIFY_USERNAME(504, "修改个人信息成功，但没有修改用户名"),
    USERNAME_EXIST(505, "用户名已存在"),
    MODIFY_HEAD_PORTRAIT_FAIL(506, "修改头像失败"),
    READ_MESSAGE_FAIL(507, "已读信息失败"),
    USERNAME_NOT_EXIST(508, "用户名不存在"),
    USERNAME_FORMAT_ERROR(509, "用户名长度过长或格式不正确"),
    PASSWORD_BLANK(510, "密码为空"),
    NAME_OR_PASSWORD_ERROR(511, "密码或用户名错误"),

    ADD_FRIEND_LINK_SUCCESS(601, "添加友链成功"),
    FRIEND_LINK_EXIST(602, "友链已存在"),
    UPDATE_FRIEND_LINK_SUCCESS(603, "更新友链成功"),
    DELETE_FRIEND_LINK_SUCCESS(604, "删除友链成功"),
    ADD_FRIEND_LINK_EXCEPTION(605, "增加友链异常"),
    UPDATE_FRIEND_LINK_EXCEPTION(606, "更新友链异常"),
    DELETE_FRIEND_LINK_EXCEPTION(607, "删除友链异常"),
    ADD_CALENDAR_FAILED(608,"添加日历失败"),
    GroupId_BLANK(609, "groupId为空"),
    PHOTOLIST_OUT(610, "photolistid超出最大范围4，异常"),
    PHOTOGROUP_NOT_CHANGE(611, "photolistid不可修改，异常"),
    DEL_PHOTO_SUCCESS(612, "删除照片成功"),
    DEL_PHOTO_FAIL(613, "删除照片失败"),
    PASSWORD_ERROR(513,"旧密码错误"),
    PASSWORD_CHANGE(600,"修改密码成功"),
    COMMENT_BLANK(801, "内容为空"),

    ;


    //信息码
    private int code;

    //详细信息
    private String message;

    //构造函数(信息码，信息)
    CodeType(int code, String message) {
        this.code = code;
        this.message = message;
    }

    //获取信息码以及详细信息
    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }


}
