package com.fmz.eternify.mb;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;

import com.fmz.eternify.controller.PessoaController;
import com.fmz.eternify.controller.UsuarioController;
import com.fmz.eternify.model.Pessoa;
import com.fmz.eternify.model.Usuario;
import com.fmz.eternify.utils.JSFHelper;

import lombok.Data;

@Named
@ViewScoped
@Data
public class PessoaMB {

	@Autowired
	private PessoaController pessoaController;

	@Autowired
	private UsuarioController usuarioController;

	private Pessoa pessoa = new Pessoa();

	private List<Pessoa> pessoas = new ArrayList<>();

	public void addPessoa() {
		Usuario usuarioLogado = JSFHelper.getUsuarioLogado();
		if (usuarioLogado.getCreditos() != null && usuarioLogado.getCreditos() > 0) {
			pessoa.setUsuario(usuarioLogado);
			pessoaController.addPessoa(pessoa);
			pessoa = new Pessoa();
			PrimeFaces.current().resetInputs("form:pnDados");
			usuarioController.debitarCredito(usuarioLogado);
			carregarLista();
			JSFHelper.addInfo("Memória cadastrada com sucesso!", "");
		} else {
			JSFHelper.addError("Créditos insuficientes!", "");
		}
	}

	@PostConstruct
	public void carregarLista() {
		Usuario usuarioLogado = JSFHelper.getUsuarioLogado();
		if (usuarioLogado != null) {
			pessoas = pessoaController.getPessoaRepository().findByUsuario(usuarioLogado);
		}
	}

}
