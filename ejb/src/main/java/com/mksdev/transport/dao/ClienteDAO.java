package com.mksdev.transport.dao;

import java.util.List;

import com.mksdev.framework.base.dao.CrudDAO;
import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.transport.entity.Cliente;
import com.mksdev.transport.entity.QCliente;
import com.mksdev.transport.filter.ClienteFilterData;

public interface ClienteDAO extends CrudDAO<Long, Cliente, QCliente> {

	public List<Cliente> findAll();
	
	public Cliente findByFilterQ(ClienteFilterData filter);
	
	public DataPage<Cliente> findByFilterQ(ClienteFilterData filter, Page page);
	
	public DataPage<Cliente> zoomQ(ClienteFilterData filter, Page page);
}
