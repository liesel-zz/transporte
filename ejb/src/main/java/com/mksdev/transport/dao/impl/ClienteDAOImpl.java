package com.mksdev.transport.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import com.mksdev.framework.base.dao.impl.CrudDAOImpl;
import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.framework.base.security.Log;
import com.mksdev.transport.dao.ClienteDAO;
import com.mksdev.transport.entity.Cliente;
import com.mksdev.transport.entity.QCliente;
import com.mksdev.transport.filter.ClienteFilterData;

@Stateless
@Log
public class ClienteDAOImpl extends CrudDAOImpl<Long, Cliente, QCliente> implements ClienteDAO {

	private static final long serialVersionUID = 1L;
	
	QCliente qCliente = QCliente.cliente;

	@Override
	public DataPage<Cliente> findByFilterQ(ClienteFilterData filter, Page page) {
		return super.findQ(qCliente, filter, page);
	}
	
	public Cliente findByFilterQ(ClienteFilterData filter){
		return super.findSingleQ(qCliente, filter);
	}
	
	@Override
	public List<Cliente> findAll() {
		return from(qCliente).list(qCliente);
	}
	
	public DataPage<Cliente> zoomQ(ClienteFilterData filter, Page page){
		return super.findQ(qCliente, filter, page);
	}
	
}
