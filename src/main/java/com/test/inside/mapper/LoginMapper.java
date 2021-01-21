package com.test.inside.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.inside.model.pojo.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;


@Mapper
@Repository
public interface LoginMapper extends BaseMapper<User> {

    User findByNameAndPassword(String name);

    void InsertNewName(@Param("userName") String name, @Param("password") String password,@Param("email") String email);

    Integer selectAllUser();

    User findByYibanid(Integer yibanid);

}