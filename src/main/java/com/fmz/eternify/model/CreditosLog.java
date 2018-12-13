package com.fmz.eternify.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "creditos_log")
@Data
@NoArgsConstructor
public class CreditosLog implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataCadastro;

	private Integer creditoAtual, creditoAcao, creditoAposAcao;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@Column(length = 1)
	private String acao;

	public static final String DEBITO = "D";
	public static final String CREDITO = "C";

	public CreditosLog(String acao, Integer creditoAtual, Integer creditoAcao, Integer creditoAposAcao, Usuario usuario) {
		super();
		this.dataCadastro = Calendar.getInstance().getTime();
		this.creditoAtual = creditoAtual;
		this.creditoAcao = creditoAcao;
		this.creditoAposAcao = creditoAposAcao;
		this.acao = acao;
		this.usuario = usuario;
	}

}
