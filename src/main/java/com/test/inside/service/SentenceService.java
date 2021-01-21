package com.test.inside.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.test.inside.model.pojo.Sentence;

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
public interface SentenceService extends IService<Sentence> {


    Map submit(List<Sentence> value, HttpServletRequest request);

    Map getByUserId(Integer id);


    void removeByUserId(Integer id);
}
