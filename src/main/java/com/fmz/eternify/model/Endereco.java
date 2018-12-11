package com.fmz.eternify.model;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Endereco {

    private String bairro, cidade, logradouro, cep, estado;
}
