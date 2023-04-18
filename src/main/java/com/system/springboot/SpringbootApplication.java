package com.system.springboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.ZoneId;
import java.util.TimeZone;


@SpringBootApplication
public class SpringbootApplication {
    public static void main(String[] args) {
        // 设置时区为上海时区，即东八区
        //TimeZone.setDefault(TimeZone.getTimeZone(ZoneId.SHORT_IDS.get("CTT")));
        //UTC时区
        //TimeZone timeZone = TimeZone.getTimeZone("UTC");
        //TimeZone.setDefault(timeZone);

        SpringApplication.run(SpringbootApplication.class, args);
    }
}
