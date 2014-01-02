package com.mksdev.transport.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.mksdev.framework.base.entity.impl.LogBaseEntityImpl;
import com.mksdev.framework.security.entity.User;

@Entity
@Table(schema = "transporte", name = "cliente")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "CLIENTE.FIND_ALL", query = "SELECT c FROM Cliente c")
})
public class Cliente extends LogBaseEntityImpl<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotEmpty
	@Column(name = "cgc", length = 14)
	private String cgc;
	
	@NotEmpty
	@Column(name = "rg", length = 10)
	private String rg;

	@NotEmpty
	@Size(min = 3, max = 100)
	private String nome;

	@Temporal(TemporalType.DATE)
	@Column(name = "data_nasc")
	private Date dataNasc;

	@Email
	@Column(name = "email", length = 254)
	private String email;

	@Column(name = "telefone", length = 20)
	private String telefone;

	@Column(name = "celular", length = 20)
	private String celular;
	
	@Column(name = "id_transportador", insertable = false, updatable = false)
	private Long transportadorId;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	@JoinColumn(name = "id_endereco", referencedColumnName = "id")
	private Endereco endereco;

	@OneToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "id_usuario", referencedColumnName = "id")
	private User usuario;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_transportador", referencedColumnName = "id")
	private Transportador transportador;

	@Version
	private Long version;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCgc() {
		return cgc;
	}

	public void setCgc(String cgc) {
		this.cgc = cgc;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}
	
	public Long getTransportadorId() {
		return transportadorId;
	}

	public void setTransportadorId(Long transportadorId) {
		this.transportadorId = transportadorId;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public User getUsuario() {
		return usuario;
	}

	public void setUsuario(User usuario) {
		this.usuario = usuario;
	}

	public Transportador getTransportador() {
		return transportador;
	}

	public void setTransportador(Transportador transportador) {
		this.transportador = transportador;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
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
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


}
