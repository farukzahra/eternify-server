package com.fmz.eternify.mb;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;

import com.fmz.eternify.controller.ConfigController;
import com.fmz.eternify.controller.FaturaController;
import com.fmz.eternify.iugu.responses.InvoiceResponse;
import com.fmz.eternify.iugu.services.Iugu;
import com.fmz.eternify.model.Fatura;
import com.fmz.eternify.model.Usuario;
import com.fmz.eternify.utils.JSFHelper;

import lombok.Data;

@Named
@ViewScoped
@Data
public class FaturaMB {

	@Autowired
	private FaturaController faturaController;
	
	@Autowired
	private ConfigController configController;


	private Fatura fatura = new Fatura();

	private List<Fatura> faturas = new ArrayList<>();

	public void addFatura() {
		Usuario usuarioLogado = JSFHelper.getUsuarioLogado();
		InvoiceResponse invoiceResponse = Iugu.criarFatura(usuarioLogado, fatura.getCreditos(), configController.getValorTransacao());
		System.out.println(invoiceResponse);
		fatura.setIdIugu(invoiceResponse.getId());
		fatura.setValorUnitario(new BigDecimal(configController.getValorTransacao() / 100));
		fatura.setValorTotal(fatura.getValorUnitario().multiply(new BigDecimal(fatura.getCreditos())));
		fatura.setUsuario(usuarioLogado);
		fatura.setStatus("pending");
		fatura.setDataCriacao(Calendar.getInstance().getTime());
		fatura.setUrl(invoiceResponse.getSecureUrl());
		faturaController.addFatura(fatura);
		fatura = new Fatura();
		PrimeFaces.current().resetInputs("form:pnDados");
		carregarLista();
        JSFHelper.addInfo("Um email será enviado com a fatura. Ou você pode acessa-la na tabela abaixo.", "");
	}

	@PostConstruct
	public void carregarLista() {
		Usuario usuarioLogado = JSFHelper.getUsuarioLogado();
		if (usuarioLogado != null) {
			faturas = faturaController.getFaturaRepository().findByUsuario(usuarioLogado);
		}
	}

}
