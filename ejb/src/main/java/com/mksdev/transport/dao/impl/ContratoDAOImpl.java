package com.mksdev.transport.dao.impl;

import javax.ejb.Stateless;

import com.mksdev.framework.base.dao.impl.CrudDAOImpl;
import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.framework.base.security.Log;
import com.mksdev.transport.dao.ContratoDAO;
import com.mksdev.transport.entity.Contrato;
import com.mksdev.transport.entity.QContrato;
import com.mksdev.transport.filter.ContratoFilterData;

@Stateless
@Log
public class ContratoDAOImpl extends CrudDAOImpl<Long, Contrato, QContrato> implements ContratoDAO {

	private static final long serialVersionUID = 1L;

	QContrato qContrato = QContrato.contrato;
	
	public Contrato findByIdQ(ContratoFilterData filter){
		return super.findSingleQ(qContrato, filter.getParamsQ(qContrato), filter.getJoinFetchQ(qContrato));
	}

	@Override
	public DataPage<Contrato> findByFilterQ(ContratoFilterData filter, Page page) {
		return super.findQ(qContrato, filter.getParamsQ(qContrato), page, filter.getJoinFetchQ(qContrato));
	}
}