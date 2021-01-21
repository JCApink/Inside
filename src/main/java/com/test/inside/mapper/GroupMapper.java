package com.test.inside.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.inside.model.pojo.Group;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Bangtidy
 * @since 2020-07-31
 */
public interface GroupMapper extends BaseMapper<Group> {

    void save(Group group);

    Group[] getByUserId(Integer id);
}
