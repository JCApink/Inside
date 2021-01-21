package com.test.inside.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.test.inside.model.pojo.Photo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.Map;

public interface PhotoService extends IService<Photo> {

    public boolean add(Photo photos,int userid);

    Map getPhotolist(Integer userid, int listid);

}
