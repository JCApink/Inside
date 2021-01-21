package com.test.inside.service.Impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.inside.mapper.SentenceMapper;
import com.test.inside.mapper.WisdomMapper;
import com.test.inside.model.pojo.Sentence;
import com.test.inside.model.pojo.User;
import com.test.inside.model.pojo.Wisdom;
import com.test.inside.service.SentenceService;
import com.test.inside.service.WisdomService;
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
 * @since 2020-08-24
 */
@Service
public class WisdomServiceImpl extends ServiceImpl<WisdomMapper, Wisdom> implements WisdomService {

    @Autowired
    RedisTemplate redisTemplate;

}
