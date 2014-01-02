package com.mksdev.transport.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.mksdev.framework.base.entity.impl.LogBaseEntityImpl;

@Entity
@Table(schema="transporte", name = "modelo_contrato")
public class ModeloContrato extends LogBaseEntityImpl<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column (name = "nome")
	private String nome;
	
	@Column(name = "titulo")
	private String titulo;
	
	@Column(name = "corpo", length = 50000)
	private String corpo;
	
	@Column(name = "rodape1")
	private String rodape1;
	
	@Column(name = "rodape2")
	private String rodape2;
	
	@Column(name = "localdata")
	private String localdata;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getCorpo() {
		return corpo;
	}

	public void setCorpo(String corpo) {
		this.corpo = corpo;
	}

	public String getRodape1() {
		return rodape1;
	}

	public void setRodape1(String rodape1) {
		this.rodape1 = rodape1;
	}

	public String getRodape2() {
		return rodape2;
	}

	public void setRodape2(String rodape2) {
		this.rodape2 = rodape2;
	}

	public String getLocaldata() {
		return localdata;
	}

	public void setLocaldata(String localdata) {
		this.localdata = localdata;
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
		ModeloContrato other = (ModeloContrato) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
