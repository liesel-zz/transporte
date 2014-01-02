package com.mksdev.transport.dao.impl;

import javax.ejb.Stateless;

import com.mksdev.framework.base.dao.impl.CrudDAOImpl;
import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.framework.base.security.Log;
import com.mksdev.transport.dao.TransportadorDAO;
import com.mksdev.transport.entity.QTransportador;
import com.mksdev.transport.entity.Transportador;
import com.mksdev.transport.filter.TransportadorFilterData;

@Stateless
@Log
public class TransportadorDAOImpl extends CrudDAOImpl<Long, Transportador, QTransportador> implements TransportadorDAO{
	
	private static final long serialVersionUID = 1L;
	
	QTransportador qTransportador = QTransportador.transportador;
	
	@Override
	public DataPage<Transportador> findByFilterQ(TransportadorFilterData filter, Page page) {
		return super.findQ(qTransportador, filter, page);
	}
	
	public Transportador findByFilterQ(TransportadorFilterData filter){
		return super.findSingleQ(qTransportador, filter);
	}
	
	public Transportador findByIdQ(Long id){
		TransportadorFilterData filter = new TransportadorFilterData();
		filter.setId(id);
		return super.findSingleQ(qTransportador, filter);
	}
	
	public Transportador findByUserQ(Long userId){
		TransportadorFilterData filter = new TransportadorFilterData();
		filter.setUserId(userId);
		return super.findSingleQ(qTransportador, filter);
	}

}
