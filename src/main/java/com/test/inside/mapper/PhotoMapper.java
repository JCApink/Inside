package com.test.inside.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.inside.model.pojo.Photo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface PhotoMapper  extends BaseMapper<Photo> {

    Photo[] getByListid(@Param("listid")int listid,@Param("userid") int userid);

    String getListtitleById(@Param("listid")int listid, @Param("userid")int userid);
}