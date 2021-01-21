package com.test.inside.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.test.inside.model.pojo.Calendar;

import com.test.inside.model.pojo.CountDown;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface CalendarMapper extends BaseMapper<Calendar> {
    List<Calendar> getPlanDate(Integer userid);
    Integer selectAll();
}
