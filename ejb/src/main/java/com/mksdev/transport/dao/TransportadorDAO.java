package com.mksdev.transport.dao;

import com.mksdev.framework.base.dao.CrudDAO;
import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.transport.entity.QTransportador;
import com.mksdev.transport.entity.Transportador;
import com.mksdev.transport.filter.TransportadorFilterData;

public interface TransportadorDAO extends CrudDAO<Long, Transportador, QTransportador> {
	
	public DataPage<Transportador> findByFilterQ(TransportadorFilterData filter, Page page);
	
	public Transportador findByFilterQ(TransportadorFilterData filter);

	public Transportador findByUserQ(Long userId);
	
	public Transportador findByIdQ(Long id);

}
