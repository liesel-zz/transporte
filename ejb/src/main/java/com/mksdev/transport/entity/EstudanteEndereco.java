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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import com.mksdev.framework.base.entity.impl.LogBaseEntityImpl;

@Entity
@Table(schema = "transporte", name = "estudante_endereco")
//@AssociationOverrides(
//		 {@AssociationOverride(name = "pk.estudante", joinColumns = @JoinColumn(name = "id_estudante")),
//		  @AssociationOverride(name = "pk.endereco",  joinColumns = @JoinColumn(name = "id_endereco"))})
@XmlRootElement
public class EstudanteEndereco extends LogBaseEntityImpl<Long> implements
		Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
//	@EmbeddedId
//	private EstudanteEnderecoPK pk = new EstudanteEnderecoPK();
	
	@JoinColumn(name = "id_estudante", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Estudante estudante;

	@JoinColumn(name = "id_endereco", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = {CascadeType.ALL})
	private Endereco endereco;
	
	@Column(name = "descricao", length = 50)
	private String descricao;

	@Override
	public Long getId() {
		return id;
	}
	
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	public Estudante getEstudante() {
		return estudante;
	}

	public void setEstudante(Estudante estudante) {
		this.estudante = estudante;
	}
	
	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
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
		EstudanteEndereco other = (EstudanteEndereco) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
