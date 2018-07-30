package com.fmz.eternify.mb;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;

import com.fmz.eternify.controller.PessoaController;
import com.fmz.eternify.model.Pessoa;

import lombok.Data;

@Named
@Data
public class PessoaMB {

	@Autowired
	private PessoaController pessoaController;

	private Pessoa pessoa = new Pessoa();

	private List<Pessoa> pessoas = new ArrayList<>();

	public void addPessoa() {
		pessoaController.addPessoa(pessoa);
		pessoa = new Pessoa();
		PrimeFaces.current().resetInputs("form:pnDados");
		carregarLista();
	}

	@PostConstruct
	public void carregarLista() {
		pessoas = pessoaController.getPessoaRepository().findAll();
	}

}
