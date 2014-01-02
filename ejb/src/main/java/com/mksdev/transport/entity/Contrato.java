package com.mksdev.transport.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.mksdev.framework.base.entity.impl.LogBaseEntityImpl;
import com.mksdev.transport.enums.SituacaoContratoEnum;
import com.mysema.query.annotations.QueryInit;

@Entity
@Table(schema="transporte", name = "contrato")
public class Contrato extends LogBaseEntityImpl<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Min(value=1)
	@Max(value=25)
	@NotNull
	@Column(name = "dia_pagto")
	private Integer diaPagto;
	
	@NotNull
	@Column(name = "vl_parcela")
	private BigDecimal vlParcelas;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "dt_ini")
	private Date dtIni;
	
	@NotNull
	@Temporal(TemporalType.DATE)
	@Column(name = "dt_fim")
	private Date dtFim;
	
	@Enumerated(EnumType.ORDINAL)
	@Column(name = "situacao")
	private SituacaoContratoEnum situacao;
	

	@Column(name = "id_cliente", insertable = false, updatable = false)
	private Long clienteId;
	
	@NotNull
	@OneToOne(fetch = FetchType.LAZY, cascade={CascadeType.REFRESH})
	@JoinColumn(name = "id_cliente", referencedColumnName = "id")
	@QueryInit("transportador.usuario")
	private Cliente cliente;
	
	@Transient
	private Boolean isSelected;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getDiaPagto() {
		return diaPagto;
	}
	public void setDiaPagto(Integer diaPagto) {
		this.diaPagto = diaPagto;
	}
	public Date getDtIni() {
		return dtIni;
	}
	public void setDtIni(Date dtIni) {
		this.dtIni = dtIni;
	}
	public Date getDtFim() {
		return dtFim;
	}
	public void setDtFim(Date dtFim) {
		this.dtFim = dtFim;
	}
	public SituacaoContratoEnum getSituacao() {
		return situacao;
	}
	public void setSituacao(SituacaoContratoEnum situacao) {
		this.situacao = situacao;
	}
	public BigDecimal getVlParcelas() {
		return vlParcelas;
	}
	public void setVlParcelas(BigDecimal vlParcelas) {
		this.vlParcelas = vlParcelas;
	}
	public Long getClienteId() {
		return clienteId;
	}
	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public Integer getQtParcelas() {
		Calendar cIni = new GregorianCalendar();
		Calendar cFim = new GregorianCalendar();
		
		cIni.setTimeInMillis(getDtIni().getTime());
		cFim.setTimeInMillis(getDtFim().getTime());
		
		return cFim.get(Calendar.MONTH) - cIni.get(Calendar.MONTH) + 1;
	}
	
	public void setQtParcelas(Integer qtParcelas) {
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
		Contrato other = (Contrato) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
