package com.omaryaya.jetbrains;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;
import java.util.TimeZone;


@SpringBootApplication
@EntityScan(basePackageClasses = { 
	JetbrainsApplication.class,
		Jsr310JpaConverters.class 
})
@EnableScheduling
public class JetbrainsApplication {

	@PostConstruct
	void init() {
		TimeZone.setDefault(TimeZone.getTimeZone("CET"));
	}

	public static void main(String[] args) {
		SpringApplication.run(JetbrainsApplication.class, args);
	}

}
