package com.fmz.eternify.iugu.services;

import com.fmz.eternify.iugu.IuguConfiguration;
import com.fmz.eternify.iugu.exceptions.IuguException;
import com.fmz.eternify.iugu.model.PaymentToken;
import com.fmz.eternify.iugu.responses.PaymentTokenResponse;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class PaymentTokenService {

	private IuguConfiguration iugu;
	private final String CREATE_URL = IuguConfiguration.url("/payment_token");

	public PaymentTokenService(IuguConfiguration iuguConfiguration) {
		this.iugu = iuguConfiguration;
	}

	public PaymentTokenResponse create(PaymentToken paymentToken) throws IuguException {
		Response response = this.iugu.getNewClientNotAuth().target(CREATE_URL).request().post(Entity.entity(paymentToken, MediaType.APPLICATION_JSON));

		int ResponseStatus = response.getStatus();
		String ResponseText = null;

		if (ResponseStatus == 200)
			return response.readEntity(PaymentTokenResponse.class);

		// Error Happened
		if (response.hasEntity())
			ResponseText = response.readEntity(String.class);

		response.close();

		throw new IuguException("Error creating token!", ResponseStatus, ResponseText);
	}
}
