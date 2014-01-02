package com.mksdev.transport.facade.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.mksdev.framework.base.facade.impl.CrudFacadeImpl;
import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.transport.dao.TransportadorDAO;
import com.mksdev.transport.entity.QTransportador;
import com.mksdev.transport.entity.Transportador;
import com.mksdev.transport.facade.TransportadorFacade;
import com.mksdev.transport.filter.TransportadorFilterData;

@Named
public class TransportadorFacadeImpl extends CrudFacadeImpl<Long, Transportador, QTransportador>
		implements TransportadorFacade {

	private static final long serialVersionUID = 1L;

	@Inject
	public TransportadorFacadeImpl(TransportadorDAO dao) {
		super(dao);
	}

	@Override
	public DataPage<Transportador> findByFilter(TransportadorFilterData filter, Page page) {
		return ((TransportadorDAO) super.getDAO()).findByFilterQ(filter, page);
	}
	
	@Override
	public Transportador findByFilter(TransportadorFilterData filter) {
		return ((TransportadorDAO) super.getDAO()).findByFilterQ(filter);
	}

	@Override
	public Transportador findByUser(Long userId) {
		return ((TransportadorDAO) super.getDAO()).findByUserQ(userId);
	}
	
	@Override
	public Transportador findById(Long id) {
		return ((TransportadorDAO) super.getDAO()).findByIdQ(id);
	}
	
}
