package com.fmz.eternify.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fmz.eternify.model.Usuario;
import com.fmz.eternify.repository.UsuarioRepository;
import com.fmz.eternify.utils.CryptMD5;
import com.fmz.eternify.utils.GeradorSenha;

import lombok.Data;

@RestController
@Data
@RequestMapping("/usuario")
public class UsuarioController {

	@Autowired
	UsuarioRepository usuarioRepository;

	@Autowired
	EmailController emailController;

	@PostMapping("/login")
	public Usuario login(@Valid @RequestBody Usuario usuario) {
		List<Usuario> usuarios = usuarioRepository.findByLoginAndSenha(usuario.getLogin(),
				CryptMD5.encrypt(usuario.getSenha()));
		return usuarios != null && !usuarios.isEmpty() ? usuarios.get(0) : null;
	}

	@PostMapping("/cadastrar")
	public Usuario cadastrar(Usuario usuario) throws Exception {
		String senha = GeradorSenha.gerarSenha();
		usuario.setSenha(CryptMD5.encrypt(senha));
		usuarioRepository.save(usuario);
		emailController.sendMailUsuarioNovo(usuario.getLogin(), senha);
		return usuario;
	}

	public String trocarSenha(Usuario usuarioLogado, String senhaAtual, String novaSenha, String novaSenhaConfirmacao)
			throws Exception {
		String senhaAtualCriptografada = CryptMD5.encrypt(senhaAtual);
		String senhaNovaCriptografada = CryptMD5.encrypt(novaSenha);

		if (novaSenhaConfirmacao.equalsIgnoreCase(novaSenha)) {
			if (usuarioLogado.getSenha().equals(senhaAtualCriptografada)) {
				if (!senhaAtualCriptografada.equals(senhaNovaCriptografada)) {
					usuarioLogado.setSenha(senhaNovaCriptografada);
					usuarioRepository.save(usuarioLogado);
				} else {
					return "Senha atual é igual a antiga";
				}
			} else {
				return "Senha atual esta errada.";
			}
		} else {
			return "A senha de confirmação esta diferente da nova senha.";
		}
		return "Senha Alterada";
	}

}
