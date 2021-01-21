package com.test.inside.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.inside.model.pojo.Changes;
import com.test.inside.model.pojo.Sentence;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Bangtidy
 * @since 2020-08-25
 */
public interface ChangesMapper extends BaseMapper<Changes> {

    void removeById(Integer userid);

    Changes[] getByUserid(Integer userid);
}
