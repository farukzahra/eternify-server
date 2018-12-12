package com.fmz.eternify.iugu.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.fmz.eternify.controller.UsuarioController;
import com.fmz.eternify.iugu.IuguConfiguration;
import com.fmz.eternify.iugu.exceptions.IuguException;
import com.fmz.eternify.iugu.model.Address;
import com.fmz.eternify.iugu.model.Customer;
import com.fmz.eternify.iugu.model.Invoice;
import com.fmz.eternify.iugu.model.Item;
import com.fmz.eternify.iugu.model.Payer;
import com.fmz.eternify.iugu.model.Subscription;
import com.fmz.eternify.iugu.responses.CustomerResponse;
import com.fmz.eternify.iugu.responses.InvoiceResponse;
import com.fmz.eternify.iugu.responses.SubscriptionResponse;
import com.fmz.eternify.model.Usuario;

public class Iugu {

	public static String TOKEN = "3f8e2c9a6564d53f6bfea048967e0bb5";

	public static final IuguConfiguration IUGU_TOKEN = new IuguConfiguration(TOKEN);

	public static final String SENHA = "";
	public static final String URL = "https://api.iugu.com/";

	public static CustomerResponse criarUsuario(Customer customer) throws Exception {
		return new CustomerService(IUGU_TOKEN).create(customer);
	}

	public static SubscriptionResponse criarPlano(Subscription subscription) throws Exception {
		return new SubscriptionService(IUGU_TOKEN).create(subscription);
	}

	public static Date buscarDataExpiracao(String idAssinatura) {
		try {
			return new SubscriptionService(IUGU_TOKEN).find(idAssinatura).getExpiresAt();
		} catch (IuguException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static boolean isSuspenso(String idAssinatura) {
		try {
			return new SubscriptionService(IUGU_TOKEN).find(idAssinatura).getSuspended();
		} catch (IuguException e) {
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) {
//		Usuario usuario = new Usuario();
//		usuario.setEmail("farukz@gmail.com");
//		usuario.setCpfCnpj("03574537948");
//		usuario.setNome("Faruk");
//		usuario.setCep("81630180");
//		usuario.setNumero(1981);
//		usuario.setIdIugu("09A55ECFDC9A45E68DBAAE142286D3EB");
//		Iugu.criarFatura(usuario, 10, 100);

		List<InvoiceResponse> buscaFaturas = Iugu.buscaFaturas("");
		for (InvoiceResponse invoiceResponse : buscaFaturas) {
			System.out.println(invoiceResponse.getSecureUrl());
		}
	}

	public static InvoiceResponse buscarFatura(String idFatura) {
		try {
			InvoiceService invoiceService = new InvoiceService(IUGU_TOKEN);
			return invoiceService.find(idFatura);
		} catch (IuguException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static InvoiceResponse criarFatura(Usuario usuario, int qtdCreditos, int valorUnitario) {
		try {
			InvoiceService invoiceService = new InvoiceService(IUGU_TOKEN);
			Item item = new Item("intelidente", qtdCreditos, valorUnitario);
			Calendar c = Calendar.getInstance();
			c.add(Calendar.DAY_OF_MONTH, 5);
			Invoice invoice = new Invoice(usuario.getEmail(), c.getTime(), item);
			Payer payer = new Payer(usuario.getCpfCnpj(), usuario.getNome(),
					new Address(usuario.getCep(), "" + usuario.getNumero()));
			invoice.setPayer(payer);
			invoice.setCustomerId(usuario.getIdIugu());
			return invoiceService.create(invoice);
		} catch (IuguException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static List<InvoiceResponse> buscaFaturas(String idAssinatura) {
		try {
			SubscriptionResponse subscriptionResponse = new SubscriptionService(IUGU_TOKEN).find(idAssinatura);
			List<InvoiceResponse> recentInvoices = new ArrayList<>();

			if (subscriptionResponse != null && subscriptionResponse.getRecentInvoices() != null
					&& !subscriptionResponse.getRecentInvoices().isEmpty()) {
				for (InvoiceResponse invoiceResponse : subscriptionResponse.getRecentInvoices()) {
					recentInvoices.add(new InvoiceService(IUGU_TOKEN).find(invoiceResponse.getId()));
				}

			}
			return recentInvoices;
		} catch (IuguException e) {
			e.printStackTrace();
		}
		return null;
	}

}
