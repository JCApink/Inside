package com.test.inside.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.test.inside.model.entity.DataMap;
import com.test.inside.model.pojo.CountDown;


/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Bangtidy
 * @since 2020-06-14
 */
public interface CountDownService extends IService<CountDown> {
    public DataMap sortDateTime(Integer userid);

    public boolean setDateTime(Integer userid, String date, String title, String content);

    public boolean deleteDateTime(Integer userid, Integer id);

}
