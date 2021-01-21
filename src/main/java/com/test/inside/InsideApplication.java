package com.test.inside;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@MapperScan("com.test.inside.mapper")
@ServletComponentScan("com.test.inside.filter")
public class InsideApplication {

	public static void main(String[] args) {
		SpringApplication.run(InsideApplication.class, args);
	}

}
