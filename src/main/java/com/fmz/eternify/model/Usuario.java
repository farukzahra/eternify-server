package com.fmz.eternify.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuario")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
public class Usuario implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String login, senha, nome, email;

	private String cpfCnpj;
	private String cep;
	private String rua;
	private String bairro;
	private String cidade;
	private String estado;
	private Integer numero;
	private String idIugu;

	private Integer creditos;

	public Usuario(@NotBlank String login, @NotBlank String senha) {
		super();
		this.login = login;
		this.senha = senha;
	}

	public String getCreditosMsg() {
		String msg = "";
		if (creditos == null || creditos == 0) {
			msg = "Você não possui nenhum crédito, compre <a href=\"fatura.jsf\" style='text-decoration: underline;color: yellow;'><span>aqui</span></a>";
		} else if (creditos == 1) {
			msg = creditos + " crédito restante";
		} else {
			msg = creditos + " créditos restantes";
		}
		return msg;
	}

}
