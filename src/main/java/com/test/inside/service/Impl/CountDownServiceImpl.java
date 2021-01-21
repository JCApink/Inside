package com.test.inside.service.Impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.inside.mapper.CountDownMapper;
import com.test.inside.model.entity.DataMap;
import com.test.inside.model.pojo.CountDown;
import com.test.inside.service.CountDownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Bangtidy
 * @since 2020-06-14
 */
@Service
public class CountDownServiceImpl extends ServiceImpl<CountDownMapper, CountDown> implements CountDownService {


    @Autowired
    private CountDownMapper countDownMapper;

    @Override
    public DataMap sortDateTime(Integer userid){
        List<CountDown> countDowns = countDownMapper.getPlanDate(userid);
        JSONArray jsonArray = new JSONArray();
        jsonArray.addAll(countDowns);
        if(countDowns.size() == 0){
            return null;
        }
        return DataMap.success().setData(jsonArray);
    }

    @Override
    public boolean setDateTime(Integer userid,String date, String title, String content){
        CountDown countDown = new CountDown();
        Timestamp ts;
        try {
            ts = Timestamp.valueOf(date);
            countDown.setDate(ts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        countDown.setUserid(userid);
        countDown.setContent(content);
        countDown.setTitle(title);
        if(countDownMapper.selectAll() == null){
            countDown.setId(1);
        }else{
            countDown.setId(countDownMapper.selectAll()+1);
        }
        countDownMapper.insert(countDown);
        return true;

    }

    @Override
    public boolean deleteDateTime(Integer userid, Integer id){
        if(id == null){
            return false;
        }
        countDownMapper.deleteById(id);
        return true;
    }

}
