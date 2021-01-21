package com.test.inside.filter;

import com.alibaba.fastjson.JSONObject;
import com.test.inside.model.entity.CodeType;
import com.test.inside.model.entity.DataMap;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 登录过滤器
 */
@WebFilter(urlPatterns = {"/*"})//过滤所有URL
public class LoginFilter implements Filter {

    //ALLOWED_PATHS 存放不需要过滤的url
    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/login", "/logout","/addphoto","/delphoto","/yiban","/yibanlogin","/addMP3","/forgetpassword","/getMP3")));

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("----Filter过滤器初始化----");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        //校验用户登录状态
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getRequestURI().substring(request.getContextPath().length()).replaceAll("[/]+$", "");
        boolean allowedPath = ALLOWED_PATHS.contains(path);

        //若该url不需要过滤
        if(allowedPath){
            //放行
            filterChain.doFilter(servletRequest,servletResponse);
        } else{

        //Filter过滤器跨域处理
        String origin = request.getHeader("Origin");
        response.setHeader("Access-Control-Allow-Origin", origin);
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "x-requested-with,Authorization,token,Content-Type");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        //获取Headers中的参数
        String token = request.getHeader("token");
        token = token == null ? "" : token;

        //查询token在Redis中的剩余时间
        Long expire = redisTemplate.getExpire(token);
        if(expire > 0){ //是登录状态
            //重置token的时间
            redisTemplate.expire(token,30L, TimeUnit.MINUTES);

            //放行
            filterChain.doFilter(servletRequest,servletResponse);
        }else{
            //未登录，响应数据
            JSONObject jsonObject = new JSONObject();
            //jsonObject.put("user","null");
            jsonObject.put("msg","未登录");
            String string = JSONObject.toJSONString(jsonObject);
            response.setContentType("json/text;charset=utf-8");
            PrintWriter out = response.getWriter();

            out.write(string);
        }
        }
    }

    @Override
    public void destroy() {
        System.out.println("----过滤器销毁----");
    }
}

