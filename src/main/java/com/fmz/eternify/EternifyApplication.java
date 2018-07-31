package com.fmz.eternify;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
//@EnableOAuth2Sso
public class EternifyApplication {

	public static void main(String[] args) {
		SpringApplication.run(EternifyApplication.class, args);
	}
}
