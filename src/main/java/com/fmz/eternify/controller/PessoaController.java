package com.fmz.eternify.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fmz.eternify.model.Pessoa;
import com.fmz.eternify.repository.PessoaRepository;

import lombok.Data;

@RestController
@Data
@RequestMapping("/pessoa")
public class PessoaController {

	@Autowired
	PessoaRepository pessoaRepository;

	@PostMapping("/addpessoa")
	public void addPessoa(@Valid @RequestBody Pessoa pessoa) {
		pessoaRepository.save(pessoa);
	}

}
