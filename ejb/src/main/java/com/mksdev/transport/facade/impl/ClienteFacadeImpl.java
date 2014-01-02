package com.mksdev.transport.facade.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import com.mksdev.framework.base.dao.exception.DaoException;
import com.mksdev.framework.base.facade.impl.CrudFacadeImpl;
import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.transport.dao.ClienteDAO;
import com.mksdev.transport.entity.Cliente;
import com.mksdev.transport.entity.QCliente;
import com.mksdev.transport.facade.ClienteFacade;
import com.mksdev.transport.facade.TransportadorFacade;
import com.mksdev.transport.filter.ClienteFilterData;

@Named
public class ClienteFacadeImpl extends CrudFacadeImpl<Long, Cliente, QCliente> implements ClienteFacade {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private TransportadorFacade transportadorFacade;

	@Inject
	public ClienteFacadeImpl(ClienteDAO dao) {
		super(dao);
	}

	@Override
	public DataPage<Cliente> findByFilter(ClienteFilterData filter, Page page) {
		return ((ClienteDAO) super.getDAO()).findByFilterQ(filter, page);
	}

	@Override
	public List<Cliente> findAll() {
		return ((ClienteDAO) super.getDAO()).findAll();
	}
	
	public Cliente findByFilter(ClienteFilterData filter){
		return ((ClienteDAO) super.getDAO()).findByFilterQ(filter);
	}
	
	@Override
	public Cliente findById(Long id){
		ClienteFilterData filter = new ClienteFilterData();
		filter.setId(id);
		return ((ClienteDAO) super.getDAO()).findByFilterQ(filter);
	}
	
	public Cliente persist(Long userId, Cliente cliente) throws ValidationException, ConstraintViolationException, DaoException{
		cliente.setTransportador(transportadorFacade.findByUser(userId));
		return super.persist(cliente);
	}
	
	public DataPage<Cliente> zoom(ClienteFilterData filter, Page page){
		return ((ClienteDAO) super.getDAO()).zoomQ(filter, page);
	}
}
