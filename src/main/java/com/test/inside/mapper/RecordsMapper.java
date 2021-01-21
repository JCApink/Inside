package com.test.inside.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.inside.model.document.Record_solr;
import com.test.inside.model.pojo.Records;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Bangtidy
 * @since 2020-08-02
 */
public interface RecordsMapper extends BaseMapper<Records> {

    //通过文章id和用户id查找文章
    Records getOneById(@Param("id")int id,@Param("userId")Integer useId);
}
