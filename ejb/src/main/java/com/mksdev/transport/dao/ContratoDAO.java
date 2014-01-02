package com.mksdev.transport.dao;

import com.mksdev.framework.base.dao.CrudDAO;
import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.transport.entity.Contrato;
import com.mksdev.transport.entity.QContrato;
import com.mksdev.transport.filter.ContratoFilterData;

public interface ContratoDAO extends CrudDAO<Long, Contrato, QContrato> {

	public Contrato findByIdQ(ContratoFilterData filter);

	public DataPage<Contrato> findByFilterQ(ContratoFilterData filter, Page page);
	
}