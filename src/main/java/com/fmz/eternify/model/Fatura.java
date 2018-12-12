package com.fmz.eternify.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "fatura")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
public class Fatura implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	private Date dataCriacao;

	private Date dataAprovacao;

	private Integer creditos;

	private String idIugu;

	private String status;

	private BigDecimal valorTotal;

	private BigDecimal valorUnitario;

	private String url;

	public String getStatusStr() {
		if ("pending".equals(status)) {
			return "pendente";
		} else if ("paid".equals(status)) {
			return "paga";
		} else if ("canceled".equals(status)) {
			return "cancelada";
		} else if ("draft".equals(status)) {
			return "rascunho";
		} else if ("partially_paid".equals(status)) {
			return "parcialmente paga";
		} else if ("refunded".equals(status)) {
			return "reembolsada";
		} else if ("expired".equals(status)) {
			return "expirada";
		} else if ("in_protest".equals(status)) {
			return "em protesto";
		} else if ("chargeback".equals(status)) {
			return "contestada";
		} else if ("in_analysis".equals(status)) {
			return "em análise";
		}
		return status;
	}

}
