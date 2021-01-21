package com.test.inside.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.test.inside.model.pojo.Sentence;
import com.test.inside.model.pojo.Text;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author Bangtidy
 * @since 2020-07-27
 */
public interface TextService extends IService<Text> {


    Map submit(List<Text> value, HttpServletRequest request);

    Map getByUserId(Integer id);
}
