package com.test.inside.service.Impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.inside.mapper.SentenceMapper;
import com.test.inside.model.pojo.Sentence;
import com.test.inside.model.pojo.User;
import com.test.inside.service.SentenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

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
public class SentenceServiceImpl extends ServiceImpl<SentenceMapper, Sentence> implements SentenceService {

    @Autowired
    RedisTemplate redisTemplate;

    @Autowired
    SentenceService sentenceService;

    @Autowired
    SentenceMapper sentenceMapper;
    @Override
    public Map submit(List<Sentence> value, HttpServletRequest request) {
        //获取用户
        String token = request.getHeader("token");
        Object user = redisTemplate.opsForValue().get(token);
        User u = (User) user;

        String[] strings = new String[value.size()];
        for (int i = 0; i < value.size(); i++) {
            strings[i] = value.get(i).getValue();
        }

        Sentence[] sentences =new Sentence[strings.length];

        sentenceService.removeByUserId(u.getId());

        for (int i = 0; i < strings.length; i++) {
            Sentence sentence = new Sentence()
                    .setValue(strings[i])
                    .setUserId(u.getId());
            sentenceService.save(sentence);
            sentences[i]=sentence;
        }
        Map<String,Sentence[]> map = new HashMap<String,Sentence[]>();
        map.put("sentenceList",sentences);
        return map;
    }

    @Override
    public Map getByUserId(Integer id) {
        Sentence[] sentences = sentenceMapper.getByUserId(id);
        Map<String,Sentence[]> map = new HashMap<String,Sentence[]>();
        map.put("sentenceList",sentences);
        return map;
    }

    @Override
    public void removeByUserId(Integer id) {
        sentenceMapper.removeByUserId(id);
    }


}
