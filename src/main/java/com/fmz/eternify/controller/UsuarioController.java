package com.fmz.eternify.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fmz.eternify.iugu.model.CustomVariable;
import com.fmz.eternify.iugu.model.Customer;
import com.fmz.eternify.iugu.responses.CustomerResponse;
import com.fmz.eternify.iugu.services.Iugu;
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
		List<Usuario> usuarios = usuarioRepository.findByLoginAndSenha(usuario.getLogin().toUpperCase(),
				CryptMD5.encrypt(usuario.getSenha()));
		return usuarios != null && !usuarios.isEmpty() ? usuarios.get(0) : null;
	}

	@PostMapping("/cadastrar")
	public String cadastrar(Usuario usuario) throws Exception {

		usuario.setLogin(usuario.getLogin().toUpperCase());

		List<Usuario> findByLogin = usuarioRepository.findByLogin(usuario.getLogin());
		if (findByLogin != null && !findByLogin.isEmpty()) {
			return "Usuário já cadastrado.";
		}

		String senha = GeradorSenha.gerarSenha();
		usuario.setSenha(CryptMD5.encrypt(senha));
		usuario.setLogin(usuario.getLogin().toUpperCase());
		usuarioRepository.save(usuario);
		emailController.sendMailUsuarioNovo(usuario, senha);
		return "Senha enviada para o seu email.";
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

	public void cadastrarIugu(Usuario usuario) throws Exception {
		Customer customerIugu = new Customer(usuario.getEmail(), usuario.getNome(), usuario.getCpfCnpj(),
				usuario.getCep(), usuario.getNumero(), usuario.getRua(), usuario.getBairro(), usuario.getCidade(),
				usuario.getEstado());
		CustomVariable cv = new CustomVariable("intelidente_2", "true");

		ArrayList<CustomVariable> cvlist = new ArrayList<>();
		cvlist.add(cv);
		customerIugu.setCustomVariables(cvlist);
		CustomerResponse usuarioIugu = Iugu.criarUsuario(customerIugu);
		usuario.setIdIugu(usuarioIugu.getId());
	}

	public void debitarCredito(Usuario usuarioLogado) {
		usuarioLogado.setCreditos(usuarioLogado.getCreditos() - 1);
		usuarioRepository.save(usuarioLogado);
	}

	public void creditarCreditos(Usuario usuario, Integer creditos) {
		Integer creditosAtuais = usuario.getCreditos() != null ? usuario.getCreditos() : 0;
		usuario.setCreditos(creditosAtuais + creditos);
		usuarioRepository.save(usuario);
	}

}
