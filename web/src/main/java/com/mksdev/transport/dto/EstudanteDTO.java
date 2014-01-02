package com.mksdev.transport.dto;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import com.mksdev.transport.entity.Estudante;
import com.mksdev.transport.entity.EstudanteEndereco;

@XmlRootElement
public class EstudanteDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Estudante estudante;

	private List<EstudanteEndereco> enderecos;
	
	public Estudante getEstudante() {
		return estudante;
	}
	
	public void setEstudante(Estudante estudante) {
		this.estudante = estudante;
	}
	
	public List<EstudanteEndereco> getEnderecos() {
		return enderecos;
	}
	
	public void setEnderecos(List<EstudanteEndereco> enderecos) {
		this.enderecos = enderecos;
	}
}
