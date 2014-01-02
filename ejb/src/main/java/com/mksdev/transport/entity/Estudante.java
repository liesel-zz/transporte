package com.mksdev.transport.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import com.mksdev.framework.base.entity.impl.LogBaseEntityImpl;
import com.mysema.query.annotations.QueryInit;

@Entity
@Table(schema="transporte", name = "estudante")
@XmlRootElement
@NamedQueries({
	@NamedQuery(name = "ESTUDANTE.FIND_ALL", query = "SELECT e FROM Estudante e") 
})
public class Estudante extends LogBaseEntityImpl<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotEmpty
	@Column(name = "nome")
	private String nome;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "data_nasc")
	private Date dataNasc;
	
	@Email
	@Size(min = 1, max = 254)
	@Column(name = "email")
	private String email;

	@Column(name = "celular", length = 20)
	private String celular;
	
//	@OneToMany(fetch = FetchType.EAGER,
//	           mappedBy = "pk.estudante",
//			   cascade = { CascadeType.ALL})
//	@Cascade( { org.hibernate.annotations.CascadeType.SAVE_UPDATE })
//	private Set<EstudanteEndereco> enderecos;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
	@JoinTable(name = "estudante_instituicao", schema = "transporte",
				joinColumns = {@JoinColumn(name = "id_estudante", nullable = false, updatable = false, referencedColumnName="id") }, 
				inverseJoinColumns = {@JoinColumn(name = "id_instituicao", nullable = false, updatable = false, referencedColumnName="id") })
	private Set<Instituicao> instituicoes;

	@ManyToOne(fetch = FetchType.LAZY, targetEntity=Cliente.class, cascade={CascadeType.REFRESH})
	@JoinColumn(name="id_cliente",referencedColumnName="id")
	@QueryInit("transportador.usuario")
	private Cliente cliente;
	
	@Version
	private Long version;
	
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

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public Set<Instituicao> getInstituicoes() {
		return instituicoes;
	}

	public void setInstituicoes(Set<Instituicao> instituicoes) {
		this.instituicoes = instituicoes;
	}
	
	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
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
		Estudante other = (Estudante) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	
}
