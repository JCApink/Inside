package com.test.inside.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.test.inside.model.pojo.Group;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Bangtidy
 * @since 2020-07-31
 */
public interface GroupService extends IService<Group> {

    Group addGroup(String value, HttpServletRequest request);

    String deleteGroup(int id, HttpServletRequest request);

    Map getGroup(HttpServletRequest request);

}
