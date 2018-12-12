package com.fmz.eternify.controller;

import java.util.Calendar;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fmz.eternify.iugu.responses.InvoiceResponse;
import com.fmz.eternify.iugu.services.Iugu;
import com.fmz.eternify.model.Fatura;
import com.fmz.eternify.model.Usuario;
import com.fmz.eternify.repository.FaturaRepository;
import com.fmz.eternify.repository.UsuarioRepository;

import lombok.Data;

@RestController
@Data
@RequestMapping("/fatura")
public class FaturaController {

	@Autowired
	FaturaRepository faturaRepository;
	
	@Autowired
	UsuarioController usuarioController;

	@PostMapping("/add")
	public void addFatura(@Valid @RequestBody Fatura Fatura) {
		faturaRepository.save(Fatura);
	}

	@GetMapping("/get")
	public Fatura findFatura(long id) {
		Fatura fatura = faturaRepository.findById(id).get();
		return fatura;
	}

	public void atualizarFaturas(Usuario usuario) {
		List<Fatura> faturas = faturaRepository.findByUsuario(usuario);
		if (faturas != null && !faturas.isEmpty()) {
			for (Fatura fatura : faturas) {
				if (fatura.getStatus().equals("pending")) {
					InvoiceResponse invoiceResponse = Iugu.buscarFatura(fatura.getIdIugu());
					if (invoiceResponse != null) {
						fatura.setStatus(invoiceResponse.getStatus());
						if(fatura.getStatus().equals("paid")) {
							fatura.setDataAprovacao(Calendar.getInstance().getTime());						
							usuarioController.creditarCreditos(usuario, fatura.getCreditos());
						}
						faturaRepository.save(fatura);
					}
				}
			}
		}

	}

}
