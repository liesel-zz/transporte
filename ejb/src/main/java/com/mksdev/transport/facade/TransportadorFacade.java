package com.mksdev.transport.facade;

import com.mksdev.framework.base.facade.CrudFacade;
import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.transport.entity.QTransportador;
import com.mksdev.transport.entity.Transportador;
import com.mksdev.transport.filter.TransportadorFilterData;

public interface TransportadorFacade extends CrudFacade<Long, Transportador, QTransportador> {
	
	public DataPage<Transportador> findByFilter(TransportadorFilterData filter, Page page);
	
	public Transportador findByFilter(TransportadorFilterData filter);

	public Transportador findByUser(Long userId);
	
	public Transportador findById(Long id);

}
