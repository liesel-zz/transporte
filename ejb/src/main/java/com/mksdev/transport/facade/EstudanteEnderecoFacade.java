package com.mksdev.transport.facade;

import java.util.List;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import com.mksdev.framework.base.dao.exception.DaoException;
import com.mksdev.framework.base.facade.CrudFacade;
import com.mksdev.transport.entity.EstudanteEndereco;
import com.mksdev.transport.entity.QEstudanteEndereco;

public interface EstudanteEnderecoFacade extends CrudFacade<Long, EstudanteEndereco, QEstudanteEndereco> {
	
	public void saveAll(List<EstudanteEndereco> enderecos) throws ConstraintViolationException, ValidationException, DaoException;
	
	public List<EstudanteEndereco> findAllEnderecoByEstudanteId(Long id);
	
	public void removeAllByEstudante(Long estudanteId) throws ConstraintViolationException, ValidationException, DaoException;
	
}