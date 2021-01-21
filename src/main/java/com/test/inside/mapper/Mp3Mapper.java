package com.test.inside.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.inside.model.pojo.MP3Info;
import com.test.inside.model.pojo.Photo_;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface Mp3Mapper extends BaseMapper<MP3Info> {

    MP3Info[] getName(Integer userid);

    Boolean deleteByName(@Param("userid")Integer userid, @Param("name")String name);
}