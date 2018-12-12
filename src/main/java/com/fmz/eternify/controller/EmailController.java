package com.fmz.eternify.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fmz.eternify.model.Usuario;

import lombok.Data;

@RestController
@Data
@RequestMapping("/email")
public class EmailController {

	@Autowired
	private JavaMailSender mailSender;

	public static final String BEM_VINDO = "bemvindo";

	public void sendMailUsuarioNovo(Usuario usuario, String senha) throws Exception {
		Map<String, String> valores = new HashMap<>();

		valores.put("#nome", usuario.getNome());
		valores.put("#usuario", usuario.getLogin());
		valores.put("#senha", senha);
		sendMail(usuario.getEmail(), "Bem vindo ao Eternify", buscarTemplate(valores, EmailController.BEM_VINDO));
	}

	public void sendMail(String to, String titulo, String corpo) throws Exception {
		MimeMessage message = mailSender.createMimeMessage();

		MimeMessageHelper helper = new MimeMessageHelper(message, true);
		helper.setTo(to);
		helper.setFrom("eternifyapp@gmail.com");
		helper.setSubject(titulo);

		helper.setText(corpo, true);

		mailSender.send(message);
	}

	public static String buscarTemplate(Map<String, String> valores, String arquivoTemplate) {
		try {
			StringBuilder sb = new StringBuilder();
			BufferedReader bf = new BufferedReader(new InputStreamReader(
					EmailController.class.getResourceAsStream("templates/" + arquivoTemplate), "UTF-8"));
			while (bf.ready()) {
				sb.append(bf.readLine());
			}
			String conteudo = sb.toString();
			if (valores != null) {
				Set<String> chaves = valores.keySet();
				for (String c : chaves) {
					String valor = valores.get(c);
					conteudo = conteudo.replace(c, valor);
				}
			}
			return conteudo;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

}
