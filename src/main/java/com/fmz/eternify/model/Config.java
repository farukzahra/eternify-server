package com.fmz.eternify.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "config")
@Data
@NoArgsConstructor
public class Config implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length=2)
	private String chave;

	@Column(length=255)
	private String valor;
	
	public static final String VALOR_TRANSACAO = "VT";

	public int getValorAsInteger() {
		return Integer.parseInt(valor);
	}

}
