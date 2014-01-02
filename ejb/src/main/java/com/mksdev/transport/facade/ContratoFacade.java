package com.mksdev.transport.facade;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import com.mksdev.framework.base.dao.exception.DaoException;
import com.mksdev.framework.base.facade.CrudFacade;
import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.transport.entity.Contrato;
import com.mksdev.transport.entity.QContrato;
import com.mksdev.transport.filter.ContratoFilterData;


public interface ContratoFacade extends CrudFacade<Long, Contrato, QContrato> {
	
	public Contrato findByFilter(ContratoFilterData filter);
	
	public Contrato findById(Long id);
	
	public DataPage<Contrato> findByFilter(ContratoFilterData filter, Page page);
	
	public Contrato persist(Contrato contrato) throws ValidationException, ConstraintViolationException, DaoException;
	
}