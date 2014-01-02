package com.mksdev.transport.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.mksdev.framework.base.entity.impl.LogBaseEntityImpl;
import com.mksdev.framework.security.entity.User;

@Entity
@Table(schema = "transporte", name = "transportador", uniqueConstraints = {
		@UniqueConstraint(columnNames = "email") })
@XmlRootElement
public class Transportador extends LogBaseEntityImpl<Long> implements
		Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "cgc", length = 14)
	private String cgc;
	
	@NotEmpty
	@Column(name = "rg", length = 10)
	private String rg;

	@NotEmpty
	@Size(min = 3, max = 100)
	@Column(name = "nome")
	private String nome;

	@Email
	@NotEmpty
	@Size(min = 1, max = 254)
	@Column(name = "email")
	private String email;

	@Column(name = "telefone", length = 20)
	private String telefone;

	@Column(name = "celular", length = 20)
	private String celular;

	@Size(min = 1, max = 10)
	@Column(name = "cod_van")
	private String codVan;
	
	@Size(min = 1, max = 10)
	@Column(name = "placa_van")
	private String placaVan;
	
	@Lob
	@Column(name = "logotipo")
	private String logotipo;
	
	@Column(name = "id_usuario", insertable = false, updatable = false)
	private Long usuarioId;

	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.REFRESH})
	@JoinColumn(name = "id_associacao", referencedColumnName = "id")
	private Associacao associacao;
	
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	@JoinColumn(name = "id_endereco", referencedColumnName = "id")
	private Endereco endereco;

	@OneToOne(fetch = FetchType.LAZY, cascade=CascadeType.REFRESH)
	@JoinColumn(name = "id_usuario", referencedColumnName = "id")
	private User usuario;

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

	public String getCodVan() {
		return codVan;
	}

	public void setCodVan(String codVan) {
		this.codVan = codVan;
	}


	public String getPlacaVan() {
		return placaVan;
	}

	public void setPlacaVan(String placaVan) {
		this.placaVan = placaVan;
	}

	public String getLogotipo() {
		return logotipo;
	}
	
	public void setLogotipo(String logotipo) {
		this.logotipo = logotipo;
	}

	public Long getUsuarioId() {
		return usuarioId;
	}

	public void setUsuarioId(Long usuarioId) {
		this.usuarioId = usuarioId;
	}

	public Associacao getAssociacao() {
		return associacao;
	}

	public void setAssociacao(Associacao associacao) {
		this.associacao = associacao;
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
		Transportador other = (Transportador) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
