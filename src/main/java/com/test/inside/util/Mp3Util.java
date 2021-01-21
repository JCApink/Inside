package com.test.inside.util;

import com.test.inside.model.pojo.MP3Info;
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

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class Mp3Util {

    /**
     * 获取MP3歌曲名、歌手、时长、照片信息
     * @param name
     * @return
     */
    public static MP3Info getMP3Info(String name) throws ReadOnlyFileException, TagException, InvalidAudioFrameException, IOException, CannotReadException {
        //String url = "D:\\study\\springboot\\inside\\photos\\user_1\\"+name;//测试
        String url = "/usr/springboot/photos/MP3/"+name;
        File sourceFile = new File(url);
        MP3File mp3File = new MP3File(sourceFile);

        //MP3File mp3File = (MP3File) AudioFileIO.read(new File(url));
        AbstractID3v2Tag v2tag = mp3File.getID3v2Tag();
        //String album = v2tag.getFirst(FieldKey.ALBUM);// 專輯名
        String artist = v2tag.getFirst(FieldKey.ARTIST);// 歌手名
        String songName = v2tag.getFirst(FieldKey.TITLE);// 歌名
        //System.out.println("album: " + album); // 專輯名
        //System.out.println("singer: " + artist); // 歌手名
        //System.out.println("songName: " + songName); // 歌名

        //MP3AudioHeader header = mp3File.getMP3AudioHeader(); // mp3文件頭部信息
        //int length = header.getTrackLength();
        //System.out.println("Length: " + length / 60 + ":" + length % 60 + "sec"); // 歌曲時長
        AbstractID3v2Tag tag = mp3File.getID3v2Tag();
        AbstractID3v2Frame frame = (AbstractID3v2Frame) tag.getFrame("APIC");

        FrameBodyAPIC body = (FrameBodyAPIC) frame.getBody();
        byte[] imageData = body.getImageData();
        Image img=Toolkit.getDefaultToolkit().createImage(imageData, 0,imageData.length);

        ImageIcon icon = new ImageIcon(img);
        String photoUrl = url.split("\\.")[0]+".jpg";
        //System.out.println(photoUrl);
        FileOutputStream fos = new FileOutputStream(photoUrl);
        fos.write(imageData);
        fos.close();

        MP3Info mp3Info = new MP3Info();
        mp3Info.setTitle(songName);
        mp3Info.setArtist(artist);
        mp3Info.setPic("http://120.79.195.7:8081/images/MP3/"+name.split("\\.")[0]+".jpg");
        mp3Info.setSrc("http://120.79.195.7:8081/images/MP3/"+name);
        return mp3Info;
    }
}
