package com.fmz.eternify.mb;

import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import com.fmz.eternify.controller.PessoaController;
import com.fmz.eternify.model.Pessoa;

import lombok.Data;

@Named
@RequestScoped
@Data
public class FindPessoaMB {

	@Autowired
	private PessoaController pessoaController;

	private Pessoa pessoa = new Pessoa();

	@PostConstruct
	public void procurarPessoa() {
		try {
			Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext()
					.getRequestParameterMap();
			String id = params.get("pessoa");
			if (id != null) {
				pessoa = pessoaController.getPessoaRepository().findById(Long.parseLong(id)).get();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
