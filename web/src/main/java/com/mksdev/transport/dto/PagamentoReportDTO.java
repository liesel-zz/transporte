package com.mksdev.transport.dto;

import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PagamentoReportDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private String transportadorNome;
	
	private String transportadorTelefone;
	
	private String clienteNome;
	
	private String estudantesNome;
		
	private Integer parcelaNumero;
	
	private Date dtVencimento;
		
	private Float valor;
	
	private ByteArrayInputStream logotipo;
	
	private ByteArrayInputStream logotipo2;

	public String getTransportadorNome() {
		return transportadorNome;
	}

	public void setTransportadorNome(String transportadorNome) {
		this.transportadorNome = transportadorNome;
	}

	public String getTransportadorTelefone() {
		return transportadorTelefone;
	}

	public void setTransportadorTelefone(String transportadorTelefone) {
		this.transportadorTelefone = transportadorTelefone;
	}

	public String getClienteNome() {
		return clienteNome;
	}

	public void setClienteNome(String clienteNome) {
		this.clienteNome = clienteNome;
	}

	public String getEstudantesNome() {
		return estudantesNome;
	}

	public void setEstudantesNome(String estudantesNome) {
		this.estudantesNome = estudantesNome;
	}

	public Integer getParcelaNumero() {
		return parcelaNumero;
	}

	public void setParcelaNumero(Integer parcelaNumero) {
		this.parcelaNumero = parcelaNumero;
	}

	public Date getDtVencimento() {
		return dtVencimento;
	}

	public void setDtVencimento(Date dtVencimento) {
		this.dtVencimento = dtVencimento;
	}

	public Float getValor() {
		return valor;
	}

	public void setValor(Float valor) {
		this.valor = valor;
	}
	
	public ByteArrayInputStream getLogotipo() {
		return logotipo;
	}

	public void setLogotipo(ByteArrayInputStream logotipo) {
		this.logotipo = logotipo;
	}
	
	public ByteArrayInputStream getLogotipo2() {
		return logotipo2;
	}

	public void setLogotipo2(ByteArrayInputStream logotipo2) {
		this.logotipo2 = logotipo2;
	}

	public Map<String, Object> convertToMap(){
		Map<String, Object> parcelasMap = new HashMap<String, Object>();
		
		parcelasMap.put("transportadorNome", this.transportadorNome);
		parcelasMap.put("transportadorTelefone", this.transportadorTelefone);
		parcelasMap.put("clienteNome", this.clienteNome);
		parcelasMap.put("estudantesNome", this.estudantesNome);
		parcelasMap.put("parcelaNumero", this.parcelaNumero);
		parcelasMap.put("dtVencimento", this.dtVencimento);
		parcelasMap.put("valor", this.valor);
		parcelasMap.put("logotipo", this.logotipo);
		parcelasMap.put("logotipo2", this.logotipo2);
		
		return parcelasMap;
	}
}
