package com.test.inside.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.test.inside.model.pojo.Group;
import com.test.inside.model.pojo.Photogroup;

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
public interface PhotogroupService extends IService<Photogroup> {

    Boolean Initialize(Integer userid);

    void createNewPhotoGroup(Integer userid);


    Map getPhotogroup(Integer userid);
}
