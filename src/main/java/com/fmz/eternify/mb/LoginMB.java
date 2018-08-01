package com.fmz.eternify.mb;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import com.fmz.eternify.controller.UsuarioController;
import com.fmz.eternify.model.Usuario;
import com.fmz.eternify.utils.JSFHelper;

import lombok.Data;

@Named
@SessionScoped
@Data
public class LoginMB {

	@Autowired
	private UsuarioController usuarioController;

	private Usuario usuario = new Usuario();

	private Usuario usuarioLogado;

	private String senhaAtual, novaSenha, novaSenhaConfirmacao;

	public String doLogin() {
		usuarioLogado = usuarioController.login(usuario);
		JSFHelper.getSession().setAttribute("USUARIO_LOGADO", usuarioLogado);
		if (usuarioLogado != null) {
			JSFHelper.redirect("pessoa.jsf");
			return "";
		}else {
			JSFHelper.addError("Email/Senha inválidos", "");
		}
		return "";
	}

	public String doCadastro() {
		try {
			String msg =  usuarioController.cadastrar(usuario);
			JSFHelper.addError(msg, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "login.xhtml";
	}

	public void doTrocarSenha() {
		try {
			String msg = usuarioController.trocarSenha(usuarioLogado, senhaAtual, novaSenha, novaSenhaConfirmacao);
			JSFHelper.addError(msg, "");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
