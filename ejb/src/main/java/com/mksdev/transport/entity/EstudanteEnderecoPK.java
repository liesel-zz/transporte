package com.mksdev.transport.entity;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Embeddable
public class EstudanteEnderecoPK implements Serializable {

	private static final long serialVersionUID = 1L;

//	@JoinColumn(name = "id_estudante", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Estudante estudante;

//	@JoinColumn(name = "id_endereco", referencedColumnName = "id")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	private Endereco endereco;

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
		result = prime * result
				+ ((endereco == null) ? 0 : endereco.hashCode());
		result = prime * result
				+ ((estudante == null) ? 0 : estudante.hashCode());
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
		EstudanteEnderecoPK other = (EstudanteEnderecoPK) obj;
		if (endereco == null) {
			if (other.endereco != null)
				return false;
		} else if (!endereco.equals(other.endereco))
			return false;
		if (estudante == null) {
			if (other.estudante != null)
				return false;
		} else if (!estudante.equals(other.estudante))
			return false;
		return true;
	}

	

}
