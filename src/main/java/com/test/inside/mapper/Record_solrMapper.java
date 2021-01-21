package com.test.inside.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.inside.model.document.Record_solr;
import com.test.inside.model.pojo.Records;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Bangtidy
 * @since 2020-08-02
 */
public interface Record_solrMapper extends BaseMapper<Record_solr> {

    List<Record_solr> getNew(Integer id);

    Record_solr[] getByStar(Integer id);

    Record_solr[] getByGroup(@Param("id")Integer name, @Param("userId")Integer id);

    Record_solr[] selectByKey(String key);
}
