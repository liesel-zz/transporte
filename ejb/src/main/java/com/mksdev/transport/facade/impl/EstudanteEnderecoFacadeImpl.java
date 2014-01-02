package com.mksdev.transport.facade.impl;

import java.util.List;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import com.mksdev.framework.base.dao.exception.DaoException;
import com.mksdev.framework.base.facade.impl.CrudFacadeImpl;
import com.mksdev.transport.dao.EstudanteEnderecoDAO;
import com.mksdev.transport.entity.EstudanteEndereco;
import com.mksdev.transport.entity.QEstudanteEndereco;
import com.mksdev.transport.facade.EstudanteEnderecoFacade;

public class EstudanteEnderecoFacadeImpl
		extends CrudFacadeImpl<Long, EstudanteEndereco, QEstudanteEndereco>
		implements EstudanteEnderecoFacade {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	public EstudanteEnderecoFacadeImpl(EstudanteEnderecoDAO dao) {
		super(dao);
	}

	@Override
	public List<EstudanteEndereco> findAllEnderecoByEstudanteId(Long id) {
		return ((EstudanteEnderecoDAO) super.getDAO()).findAllByEstudanteId(id);
	}

	@Override
	public void saveAll(List<EstudanteEndereco> enderecos) throws ConstraintViolationException, ValidationException, DaoException {
		for (EstudanteEndereco estudanteEndereco : enderecos) {
			super.save(estudanteEndereco);
		}
	}
	
	@Override
	public void removeAllByEstudante(Long estudanteId) throws ConstraintViolationException, ValidationException, DaoException{
		List<EstudanteEndereco> enderecos = findAllEnderecoByEstudanteId(estudanteId);
		for (EstudanteEndereco estudanteEndereco : enderecos) {
			super.remove(estudanteEndereco);
		}
	}

}
