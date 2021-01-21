package com.test.inside.service;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.test.inside.model.entity.DataMap;
import com.test.inside.model.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.swing.text.StyledEditorKit;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Bangtidy
 * @since 2020-06-10
 */
public interface UserService extends IService<User> {
    public DataMap login(String name, String Password);
    public boolean register(String name, String password,String email);

    DataMap logout(String token);

    public DataMap forget(String name, String email);

    public boolean reset(User user, String newpassword);

    public JSONObject yiban(Integer userid, String username);
}

