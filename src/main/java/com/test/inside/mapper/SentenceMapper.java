package com.test.inside.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.inside.model.pojo.Sentence;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Bangtidy
 * @since 2020-07-27
 */
public interface SentenceMapper extends BaseMapper<Sentence> {

    Sentence[] getByUserId(Integer id);

    void removeByUserId(Integer id);
}
