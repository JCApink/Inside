package com.test.inside.service;

import com.baomidou.mybatisplus.extension.service.IService;

import com.test.inside.model.pojo.Photo_;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface Photo_Service extends IService<Photo_> {

    Map add(MultipartFile photos) throws UnsupportedEncodingException;

    Boolean del(String name,Integer userid) throws UnsupportedEncodingException;

}
