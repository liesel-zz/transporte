package com.mksdev.transport.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.mksdev.framework.base.entity.impl.LogBaseEntityImpl;
import com.mksdev.transport.enums.SituacaoPagamentoEnum;
import com.mysema.query.annotations.QueryInit;

@Entity
@Table(schema="transporte", name = "pagamento", 
	   uniqueConstraints={@UniqueConstraint(columnNames={"ano", "mes", "id_contrato"})}
)
@NamedQueries({
	@NamedQuery(name = "PAGAMENTO.FIND_ALL", query = "SELECT p FROM Pagamento p")
})
public class Pagamento extends LogBaseEntityImpl<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "ano")
	private Integer ano;
	
	@Column(name = "mes")
	private Integer mes;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dt_vencimento")
	private Date dtVencimento;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dt_pagamento")
	private Date dtPagamento;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "situacao")
	private SituacaoPagamentoEnum situacao;
	
	@Column(name = "valor")
	private BigDecimal valor;
	
	@Column(name="id_contrato", insertable=false, updatable=false)
	private Long contratoId;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade={CascadeType.REFRESH})
	@JoinColumn(name = "id_contrato", referencedColumnName = "id")
	@QueryInit(value="cliente.transportador.usuario")
	private Contrato contrato;
	
	@Transient
	private Boolean isSelected;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public Date getDtVencimento() {
		return dtVencimento;
	}

	public void setDtVencimento(Date dtVencimento) {
		this.dtVencimento = dtVencimento;
	}

	public Date getDtPagamento() {
		return dtPagamento;
	}

	public void setDtPagamento(Date dtPagamento) {
		this.dtPagamento = dtPagamento;
	}

	public SituacaoPagamentoEnum getSituacao() {
		return situacao;
	}

	public void setSituacao(SituacaoPagamentoEnum situacao) {
		this.situacao = situacao;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}


	public Long getContratoId() {
		return contratoId;
	}

	public void setContratoId(Long contratoId) {
		this.contratoId = contratoId;
	}

	public Contrato getContrato() {
		return contrato;
	}

	public void setContrato(Contrato contrato) {
		this.contrato = contrato;
	}
	
	public Boolean getIsSelected() {
		return isSelected;
	}
	public void setIsSelected(Boolean isSelected) {
		this.isSelected = isSelected;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pagamento other = (Pagamento) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
