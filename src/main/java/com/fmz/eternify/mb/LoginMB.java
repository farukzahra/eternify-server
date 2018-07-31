package com.fmz.eternify.mb;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import com.fmz.eternify.controller.UsuarioController;
import com.fmz.eternify.model.Usuario;
import com.fmz.eternify.utils.JSFHelper;

import lombok.Data;

@Named
@Data
public class LoginMB {

	@Autowired
	private UsuarioController usuarioController;

	private Usuario usuario = new Usuario();

	private Usuario usuarioLogado;

	private String senhaAtual, novaSenha, novaSenhaConfirmacao;

	public String doLogin() {
		usuarioLogado = usuarioController.login(usuario);
		if (usuarioLogado != null) {
			JSFHelper.redirect("pessoa.jsf");
			return "";
		}else {
			// TODO COLOCAR MSG de ERRO
		}
		return "";
	}

	public String doCadastro() {
		try {
			Usuario usuarioCadastro = usuarioController.cadastrar(usuario);
			// TODO COLOCAR MSG DE EMAIL ENVIADO
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "login.xhtml";
	}

	public void doTrocarSenha() {
		try {
			// TODO COLOCAR MSG
			String msg = usuarioController.trocarSenha(usuarioLogado, senhaAtual, novaSenha, novaSenhaConfirmacao);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
