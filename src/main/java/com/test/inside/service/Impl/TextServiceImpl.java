package com.test.inside.service.Impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.inside.mapper.TextMapper;
import com.test.inside.model.pojo.Text;
import com.test.inside.model.pojo.User;
import com.test.inside.service.TextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Bangtidy
 * @since 2020-07-27
 */
@Service
public class TextServiceImpl extends ServiceImpl<TextMapper, Text> implements TextService {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    TextService textService;

    @Autowired
    TextMapper textMapper;


    @Override
    public Map submit(List<Text> value, HttpServletRequest request) {
        //获取用户
        String token = request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        User u = (User) user;

        String[] strings = new String[value.size()];
        for (int i = 0; i < value.size(); i++) {
            strings[i] = value.get(i).getContent();
        }

        Text[] texts = new Text[strings.length];

        textMapper.removeByUserId(u.getId());

        for (int i = 0; i < strings.length; i++) {
            Text text = new Text()
                    .setContent(strings[i])
                    .setUserid(u.getId());
            textService.save(text);
            texts[i]=text;
        }
        Map<String,Text[]> map = new HashMap<String,Text[]>();
        map.put("textList",texts);
        return map;
    }

    @Override
    public Map getByUserId(Integer id) {
            Text[] texts = textMapper.getByUserId(id);
            Map<String,Text[]> map = new HashMap<String,Text[]>();
            map.put("textList",texts);
            return map;
        }
}


