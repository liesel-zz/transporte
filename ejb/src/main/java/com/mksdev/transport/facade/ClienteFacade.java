package com.mksdev.transport.facade;

import java.util.List;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import com.mksdev.framework.base.dao.exception.DaoException;
import com.mksdev.framework.base.facade.CrudFacade;
import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.transport.entity.Cliente;
import com.mksdev.transport.entity.QCliente;
import com.mksdev.transport.filter.ClienteFilterData;

public interface ClienteFacade extends CrudFacade<Long, Cliente, QCliente> {
	
	public List<Cliente> findAll();
	
	public DataPage<Cliente> findByFilter(ClienteFilterData filter, Page page);
	
	public Cliente findByFilter(ClienteFilterData filter);
	
	public Cliente findById(Long id);
	
	public DataPage<Cliente> zoom(ClienteFilterData filter, Page page);
	
	public Cliente persist(Long userId, Cliente cliente) throws ValidationException, ConstraintViolationException, DaoException;
}
