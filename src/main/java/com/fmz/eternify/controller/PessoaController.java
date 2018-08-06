package com.fmz.eternify.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

	@PostMapping("/add")
	public void addPessoa(@Valid @RequestBody Pessoa pessoa) {
		pessoaRepository.save(pessoa);
	}

	@GetMapping("/get")
	public Pessoa findPessoa(long id) {
		Pessoa pessoa = pessoaRepository.findById(id).get();
		if (pessoa != null) {
			pessoa.setDataHora(new SimpleDateFormat("dd/MM/yyyy HH:mm").format(Calendar.getInstance().getTime()));
		}
		return pessoa;
	}

}
