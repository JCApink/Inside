package com.test.inside.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.test.inside.mapper.Mp3Mapper;
import com.test.inside.mapper.Photo_Mapper;
import com.test.inside.model.pojo.MP3Info;
import com.test.inside.model.pojo.Photo_;
import com.test.inside.service.Mp3Service;
import com.test.inside.service.Photo_Service;
import com.test.inside.util.Mp3Util;
import com.test.inside.util.Topingyin;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.AbstractID3v2Frame;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.jaudiotagger.tag.id3.framebody.FrameBodyAPIC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Service
public class Mp3ServiceImpl extends ServiceImpl<Mp3Mapper, MP3Info> implements Mp3Service {

    @Autowired
    Mp3Mapper mp3Mapper;

    @Autowired
    Mp3Service mp3Service;

    @Override
    public Map add(MultipartFile upload) throws IOException {
        {
            System.out.println("执行方法");
            //String filePath = "D:\\study\\springboot\\inside\\photos\\user_1"+"\\";//测试
            String filePath = "/usr/springboot/photos/MP3/";
            //判断文件夹是否存在,不存在则创建
            File file=new File(filePath);
           /* Mp3Util mp3Util = new Mp3Util();
            MP3Info mp3Info = mp3Util.getMP3Info((File) upload);*/
            Map<String,Object> map = new HashMap<String,Object>();
            if(!file.exists()){
                System.out.println("创建新文件夹"+filePath);
                file.mkdirs();
            }
            //String url = "D:\\CloudMusic\\Ada - new sou.mp3";
            //MP3Info mp3Info = Mp3Util.getMP3Info(url);
            String originalFileName = upload.getOriginalFilename();//获取原始图片的扩展名
            String newFileName = Topingyin.trans2PinYin(originalFileName);//将名字转化为拼音
            String newFilePath=filePath+newFileName; //新文件的路径
            System.out.println(newFilePath);

            try {
               upload.transferTo(new File(newFilePath));//将传来的文件写入新建的文件

                MP3Info mp3Info = new MP3Info();
                mp3Info.setTitle(newFileName);
                //mp3Info.setUserid(1);

                //mp3Service.save(mp3Info);
                map.put("music",mp3Info);
                //返回数据
                System.out.println("上传图片成功进行上传文件测试");
                return map;
            }catch (IllegalStateException e ) {
                //处理异常
            }
            return null;
        }
    }

    @Override
    public Map get(Integer userid) throws ReadOnlyFileException, CannotReadException, TagException, InvalidAudioFrameException, IOException {
       // MP3Info mp3Info = Mp3Util.getMP3Info("/usr/springboot/photos/MP3/"+name);

        MP3Info[] result = mp3Mapper.getName(userid);
        MP3Info[] mp3Infos = new MP3Info[result.length];
        for (int i = 0; i < result.length; i++) {
            mp3Infos[i] = Mp3Util.getMP3Info(result[i].getTitle());
        }
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("photo",mp3Infos);
        return map;
    }

    @Override
    public Boolean deleteByName(Integer userid, String name) {
        if(name == null)
            return false;
        mp3Mapper.deleteByName(userid,name);
        return true;
    }
}
