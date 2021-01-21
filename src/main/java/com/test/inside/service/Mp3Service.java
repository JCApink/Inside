package com.test.inside.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.test.inside.model.pojo.MP3Info;
import com.test.inside.model.pojo.Photo_;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

public interface Mp3Service extends IService<MP3Info> {

    Map add(MultipartFile file) throws ReadOnlyFileException, TagException, InvalidAudioFrameException, IOException, CannotReadException;

    Map get(Integer userid) throws ReadOnlyFileException, CannotReadException, TagException, InvalidAudioFrameException, IOException;

    Boolean deleteByName(Integer userid, String name);
}
