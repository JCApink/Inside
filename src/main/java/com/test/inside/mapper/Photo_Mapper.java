package com.test.inside.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.inside.model.pojo.Photo_;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface Photo_Mapper extends BaseMapper<Photo_> {

    void deleteByName(String name);
}