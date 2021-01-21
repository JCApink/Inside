package com.test.inside.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.inside.model.pojo.Photogroup;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Bangtidy
 * @since 2020-07-31
 */
public interface PhotogroupMapper extends BaseMapper<Photogroup> {

    String[] getOwnPhototitle(Integer userid);

    Photogroup[] getByUserid(Integer userid);

    String getListtitle(@Param("listid")int listid, @Param("userid")Integer userid);
}
