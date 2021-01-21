package com.test.inside.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.inside.model.pojo.Sentence;
import com.test.inside.model.pojo.Text;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Bangtidy
 * @since 2020-07-27
 */
public interface TextMapper extends BaseMapper<Text> {

    void removeByUserId(Integer id);

    Text[] getByUserId(Integer id);
}
