package com.test.inside.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.inside.model.pojo.Sentence;
import com.test.inside.model.pojo.Wisdom;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Bangtidy
 * @since 2020-08-24
 */
public interface WisdomMapper extends BaseMapper<Wisdom> {
    Wisdom getwisdom();

}
