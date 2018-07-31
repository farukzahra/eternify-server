package com.fmz.eternify.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

@RestController
@Data
@RequestMapping("/email")
public class EmailController {

	@Autowired
	private JavaMailSender mailSender;

	public void sendMailUsuarioNovo(String email, String senha) throws Exception {
		SimpleMailMessage message = new SimpleMailMessage();

		message.setSubject("Bem vindo ao Eternify");
		message.setText("Bem vindo ao Eternify, esta é sua senha de acesso : " + senha);
		message.setTo(email);
		message.setFrom("eternifyapp@gmail.com");
		mailSender.send(message);
	}

}
