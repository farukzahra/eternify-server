package com.fmz.eternify.model;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fmz.eternify.utils.Utils;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "pessoa")
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
public class Pessoa implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "id_usuario")
	private Usuario usuario;

	@NotBlank
	private String nome;

	@Column(length = 100000)
	private String foto;

	private String religiao;

	private String profissao;

	private String citacao;
	
	private String facebook;
	
	private String instagram;
	
	private String twitter;
	
	private String youtube;

	@NotBlank
	@Column(length = 100000)
	private String descricao;

	private Integer filhos = 0;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataNascimento;

	@Temporal(TemporalType.TIMESTAMP)
	private Date dataFalecimento;

	@Column(nullable = false, updatable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@CreatedDate
	private Date createdAt;

	@Column(nullable = false)
	@Temporal(TemporalType.TIMESTAMP)
	@LastModifiedDate
	private Date updatedAt;

	public String getQrCode() {
		return "http://eternify.com.br/findpessoa.jsf?pessoa=" + id;
	}

	public String getDataNascimentoStr() {
		return Utils.dateToString(this.dataNascimento, "dd/MM/yyyy");

	}

	public String getDescricaoFormatado() {
		String retorno = "";
		if (descricao != null) {
			String[] split = descricao.split("\\r?\\n");
			for (String string : split) {
				retorno += "<p>" + string + "</p>";
			}
		}
		return retorno;
	}

	public String getDataFalecimentoStr() {
		return Utils.dateToString(this.dataFalecimento, "dd/MM/yyyy");

	}

	public String getFacebookUrl() {
		String url = Utils.splitURL(facebook);
		if(url != null) {
			return "facebook/"+url;
		}else {
			return "";
		}
	}
	
	public String getInstagramUrl() {
		String url = Utils.splitURL(instagram);
		if(url != null) {
			return "instagram/"+url;
		}else {
			return "";
		}
	}
	
	public String getTwitterUrl() {
		String url = Utils.splitURL(twitter);
		if(url != null) {
			return "twitter/"+url;
		}else {
			return "";
		}
	}
	
	public String getYoutubeUrl() {
		String url = Utils.splitURL(youtube);
		if(url != null) {
			return "youtube/"+url;
		}else {
			return "";
		}
	}
	

	public String getIdade() {
		Instant instant = dataNascimento.toInstant();
		ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
		LocalDate lddataNascimento = zdt.toLocalDate();

		Instant instant2 = dataFalecimento.toInstant();
		ZonedDateTime zdt2 = instant2.atZone(ZoneId.systemDefault());
		LocalDate lddataFalecimento = zdt2.toLocalDate();

		if ((dataNascimento != null) && (dataFalecimento != null)) {
			int anos = Period.between(lddataNascimento, lddataFalecimento).getYears();
			if (anos <= 0) {
				int meses = Period.between(lddataNascimento, lddataFalecimento).getMonths();
				if (meses > 1) {
					return meses + " meses";
				} else {
					return meses + " mês";
				}
			} else if (anos == 1) {
				return anos + " ano";
			} else {
				return anos + " anos";
			}
		} else {
			return "0";
		}

	}
}
