package com.test.inside.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.inside.model.pojo.Todo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Bangtidy
 * @since 2020-06-14
 */
@Mapper
@Repository
public interface TodoMapper extends BaseMapper<Todo> {
    List<Todo> getPlanDate(Integer userid);

    Integer selectAll();
}
