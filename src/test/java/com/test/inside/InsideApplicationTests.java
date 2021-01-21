package com.test.inside;

import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.audio.exceptions.CannotReadException;
import org.jaudiotagger.audio.mp3.MP3AudioHeader;
import org.jaudiotagger.audio.mp3.MP3File;
import org.jaudiotagger.tag.FieldKey;
import org.jaudiotagger.tag.id3.AbstractID3v2Tag;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.*;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.jaudiotagger.audio.exceptions.InvalidAudioFrameException;
import org.jaudiotagger.audio.exceptions.ReadOnlyFileException;
import org.jaudiotagger.tag.TagException;
import org.jaudiotagger.tag.id3.AbstractID3v2Frame;
import org.jaudiotagger.tag.id3.framebody.FrameBodyAPIC;

@RestController
@CrossOrigin
@SpringBootTest
class InsideApplicationTests {
	public static class TestMP3 {
		public static void main(String[] args) throws ReadOnlyFileException, TagException, InvalidAudioFrameException, IOException, CannotReadException {


			String url = "D:\\CloudMusic\\Ada - new sou.mp3";
			File sourceFile = new File(url);

			MP3File mp3File = (MP3File) AudioFileIO.read(new File("D:\\CloudMusic\\Ada - new sou.mp3"));
			AbstractID3v2Tag v2tag = mp3File.getID3v2Tag();

			String artist = v2tag.getFirst(FieldKey.ARTIST);// 歌手名
			String album = v2tag.getFirst(FieldKey.ALBUM);// 專輯名
			String songName = v2tag.getFirst(FieldKey.TITLE);// 歌名
			System.out.println("album: " + album); // 專輯名
			System.out.println("singer: " + artist); // 歌手名
			System.out.println("songName: " + songName); // 歌名

			MP3AudioHeader header = mp3File.getMP3AudioHeader(); // mp3文件頭部信息
			int length = header.getTrackLength();
			System.out.println("Length: " + length / 60 + ":" + length % 60 + "sec"); // 歌曲時長
			AbstractID3v2Tag tag = mp3File.getID3v2Tag();
			AbstractID3v2Frame frame = (AbstractID3v2Frame) tag.getFrame("APIC");

			FrameBodyAPIC body = (FrameBodyAPIC) frame.getBody();
			byte[] imageData = body.getImageData();
					//System.out.println(imageData);
			Image img=Toolkit.getDefaultToolkit().createImage(imageData, 0,imageData.length);
			System.out.println("img----" + imageData);
					ImageIcon icon = new ImageIcon(img);
					FileOutputStream fos = new FileOutputStream("D://test1.jpg");
					fos.write(imageData);
					fos.close();
					getImg(icon);
				}

				public static void getImg(ImageIcon img){
					JFrame f = new JFrame();
					JLabel l = new JLabel();
					l.setIcon(img);
					l.setVisible(true);
					f.add(l);
					f.setSize(500, 500);
					f.setVisible(true);
				}
		}
}

