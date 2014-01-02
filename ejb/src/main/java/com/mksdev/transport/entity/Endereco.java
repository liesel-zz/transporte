package com.mksdev.transport.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;

import com.mksdev.framework.base.entity.impl.LogBaseEntityImpl;

@Entity
@Table(schema="transporte", name = "endereco")
public class Endereco extends LogBaseEntityImpl<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Column(name = "endereco")
	private String endereco;
	
	@NotEmpty
	@Column(name = "bairro")
	private String bairro;
	
	@NotEmpty
	@Column(name = "cidade")
	private String cidade;
	
	@NotEmpty
	@Size(min = 2, max = 2)
	@Column(name = "uf", length = 2)
	private String uf;
	
	@NotEmpty
	@Column(name = "pais")
	private String pais;
	
	@Column(name = "numero", length=6)
	private Integer numero;
	
	@NotNull
	@Column(name = "cep")
	private Integer cep;
	
	@Column(name = "complemento")
	private String complemento;
	
	@NotNull
	@Column(name = "latitude")
	private Double latitude;
	
	@NotNull
	@Column(name = "longitude")
	private Double longitude;
			
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	public String getBairro() {
		return bairro;
	}
	public void setBairro(String bairro) {
		this.bairro = bairro;
	}
	public String getCidade() {
		return cidade;
	}
	public void setCidade(String cidade) {
		this.cidade = cidade;
	}
	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public Integer getNumero() {
		return numero;
	}
	public void setNumero(Integer numero) {
		this.numero = numero;
	}
	public Integer getCep() {
		return cep;
	}
	public void setCep(Integer cep) {
		this.cep = cep;
	}
	public String getComplemento() {
		if (complemento == null)
			return "";
		return complemento;
		
	}
	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}
	public Double getLatitude() {
		return latitude;
	}
	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	public Double getLongitude() {
		return longitude;
	}
	public void setLongitude(Double longitude) {
		this.longitude = longitude;
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
		Endereco other = (Endereco) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
