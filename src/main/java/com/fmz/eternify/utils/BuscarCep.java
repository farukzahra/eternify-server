package com.fmz.eternify.utils;

import org.springframework.web.client.RestTemplate;

import com.fmz.eternify.model.Endereco;

public class BuscarCep {

    public static Endereco buscarCep(String cep) {
        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject("https://api.postmon.com.br/v1/cep/" + cep, Endereco.class);
    }

}
