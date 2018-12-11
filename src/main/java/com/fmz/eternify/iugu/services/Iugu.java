package com.fmz.eternify.iugu.services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

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
        Iugu iugu = new Iugu();
        iugu.criarFatura();
    }

    public static void criarFatura() {
        try {
            InvoiceService invoiceService = new InvoiceService(IUGU_TOKEN);
            Item item = new Item("catchme", 100, 10);
            Calendar c = Calendar.getInstance();
            c.add(Calendar.DAY_OF_MONTH, 5);
            Invoice invoice = new Invoice("faruk.zahra@lumetec.com.br", c.getTime(), item);
            Payer payer = new Payer("03574537948", "FMZ", new Address("81630-180", "1981"));
            invoice.setPayer(payer);
            invoiceService.create(invoice);
        } catch (IuguException e) {
            e.printStackTrace();
        }
    }

    public static List<InvoiceResponse> buscaFaturas(String idAssinatura) {
        try {
            SubscriptionResponse subscriptionResponse = new SubscriptionService(IUGU_TOKEN).find(idAssinatura);
            List<InvoiceResponse> recentInvoices = new ArrayList<>();

            if (subscriptionResponse != null && subscriptionResponse.getRecentInvoices() != null && !subscriptionResponse.getRecentInvoices().isEmpty()) {
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
