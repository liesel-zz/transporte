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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.mksdev.framework.base.entity.impl.LogBaseEntityImpl;

@Entity
@Table(schema="transporte", name = "associacao")
@NamedQueries({
	@NamedQuery(name = "ASSOCIACAO.FIND_BY_FILTER", query = "SELECT a FROM Associacao a "
														  + "WHERE UPPER(a.nomeAbrev) LIKE UPPER(:nomeAbrev) "),
	@NamedQuery(name = "ASSOCIACAO.COUNT_FIND_BY_FILTER", query = "SELECT count(*) FROM Associacao a "
																+ "WHERE UPPER(a.nomeAbrev) LIKE UPPER(:nomeAbrev) "),
	@NamedQuery(name = "ASSOCIACAO.ZOOM", query = "SELECT a FROM Associacao a "
												+ "WHERE UPPER(a.nomeAbrev) LIKE UPPER(:nomeAbrev) "),
	@NamedQuery(name = "ASSOCIACAO.COUNT_ZOOM", query = "SELECT count(*) FROM Associacao a "
													  + "WHERE UPPER(a.nomeAbrev) LIKE UPPER(:nomeAbrev) ")})
public class Associacao extends LogBaseEntityImpl<Long> implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "nome")
	private String nome;
	
	@Column(name = "nome_abrev")
	private String nomeAbrev;
	
	@Column(name = "id_modelo_contrato", insertable = false, updatable = false)
	private Long modeloContratoId;
	
	@ManyToOne(fetch = FetchType.LAZY, targetEntity=ModeloContrato.class, cascade={CascadeType.REFRESH})
	@JoinColumn(name="id_modelo_contrato", referencedColumnName="id")
	private ModeloContrato modeloContrato;
	
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

	public String getNomeAbrev() {
		return nomeAbrev;
	}

	public void setNomeAbrev(String nomeAbrev) {
		this.nomeAbrev = nomeAbrev;
	}

	public Long getModeloContratoId() {
		return modeloContratoId;
	}

	public void setModeloContratoId(Long modeloContratoId) {
		this.modeloContratoId = modeloContratoId;
	}

	public ModeloContrato getModeloContrato() {
		return modeloContrato;
	}

	public void setModeloContrato(ModeloContrato modeloContrato) {
		this.modeloContrato = modeloContrato;
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
		Associacao other = (Associacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
