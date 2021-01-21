package com.test.inside.service.Impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.inside.mapper.LoginMapper;
import com.test.inside.mapper.UserMapper;
import com.test.inside.model.entity.DataMap;
import com.test.inside.model.entity.MD5;
import com.test.inside.model.pojo.User;
import com.test.inside.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.Duration;
import java.util.UUID;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Bangtidy
 * @since 2020-06-10
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private LoginMapper loginmapper;

    @Resource
    RedisTemplate<String,Object> redisTemplate;

    @Override
    public DataMap login(String name, String password){
        User user =  loginmapper.findByNameAndPassword(name);
        if(user != null){
            if(user.getPassword().equals(MD5.code(password))){
                //生成token令牌
                String token = UUID.randomUUID()+"";
                //存到Redis数据库
                redisTemplate.opsForValue().set(token,user, Duration.ofMinutes(30L));

                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id",user.getId());
                jsonObject.put("name",user.getUserName());
                jsonObject.put("token",token);
                return DataMap.success().setData(jsonObject);
            }
            else{
                return null;
            }
        }
        return null;
    }

    @Override
    public boolean register(String name,String password, String email){
        User user = loginmapper.findByNameAndPassword(name);
        if(user != null){
            return false;
        }
        System.out.println(loginmapper.selectAllUser());

        //不能使用前面的user会报空指针异常
        User user1= new User();
        user1.setId(loginmapper.selectAllUser()+1);
        user1.setUserName(name);
        user1.setPassword(MD5.code(password));
        user1.setEmail(email);
        System.out.println(user1);
        loginmapper.insert(user1);
        return true;


    }

    @Override
    public DataMap logout(String token) {
        Boolean delete = redisTemplate.delete(token);

        JSONObject jsonObject = new JSONObject();
        if(delete){
            jsonObject.put("msg","退出成功");
        }else{
            jsonObject.put("msg","未登录");
        }
        return DataMap.success().setData(jsonObject);
    }

    @Override
    public DataMap forget(String name, String email){
        User user = loginmapper.findByNameAndPassword(name);
        System.out.println(user);
        if(user!=null){
            System.out.println(user.getEmail().equals(email));
            System.out.println(user.getEmail());
            System.out.println(email);
            if(user.getEmail().equals(email)){
                String token = UUID.randomUUID()+"";
                redisTemplate.opsForValue().set(token,user, Duration.ofMinutes(30L));
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("token", token);
                return DataMap.success().setData(jsonObject);
            }else{
                return null;
            }
        }

        return null;
    }

    @Override
    public boolean reset(User user, String newpassword){
        if(user == null){
            return false;
        }
        user.setPassword(MD5.code(newpassword));
        loginmapper.updateById(user);
        return true;
    }

    @Override
    public JSONObject yiban(Integer yibanid, String username){
        User user = loginmapper.findByYibanid(yibanid);
        if(user != null){
            //生成token令牌
            String token = UUID.randomUUID()+"";
            //存到Redis数据库
            redisTemplate.opsForValue().set(token,user, Duration.ofMinutes(30L));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", user.getId());
            jsonObject.put("name", user.getUserName());
            jsonObject.put("token",token);
            return jsonObject;
        }else{
            User user1= new User();
            user1.setYibanid(yibanid);
            user1.setId(loginmapper.selectAllUser());
            user1.setUserName(username);
            user1.setPassword(MD5.code(username));
            user1.setEmail("123456@qq.com");
            System.out.println(user1);
            loginmapper.insert(user1);
            //生成token令牌
            String token = UUID.randomUUID()+"";
            //存到Redis数据库
            redisTemplate.opsForValue().set(token,user1, Duration.ofMinutes(30L));
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", user1.getId());
            jsonObject.put("name",username);
            jsonObject.put("token",token);
            return jsonObject;
        }
    }


}