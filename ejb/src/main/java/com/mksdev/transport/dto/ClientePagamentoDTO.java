package com.mksdev.transport.dto;

import java.util.Date;

import com.mksdev.transport.enums.SituacaoContratoEnum;

public class ClientePagamentoDTO {
	
	private Long clienteId;
	private String clienteNome;
	private Date dtVencimento;
	private SituacaoContratoEnum situacao;

	public Long getClienteId() {
		return clienteId;
	}
	public void setClienteId(Long clienteId) {
		this.clienteId = clienteId;
	}
	public String getClienteNome() {
		return clienteNome;
	}
	public void setClienteNome(String clienteNome) {
		this.clienteNome = clienteNome;
	}
	public Date getDtVencimento() {
		return dtVencimento;
	}
	public void setDtVencimento(Date dtVencimento) {
		this.dtVencimento = dtVencimento;
	}
	public SituacaoContratoEnum getSituacao() {
		return situacao;
	}
	public void setSituacao(SituacaoContratoEnum situacao) {
		this.situacao = situacao;
	}
	
}
