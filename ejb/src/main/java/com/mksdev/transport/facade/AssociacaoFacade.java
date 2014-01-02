package com.mksdev.transport.facade;

import java.util.Map;

import com.mksdev.framework.base.facade.CrudFacade;
import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.transport.entity.Associacao;
import com.mksdev.transport.entity.QAssociacao;
import com.mksdev.transport.filter.AssociacaoFilterData;

public interface AssociacaoFacade extends CrudFacade<Long, Associacao, QAssociacao> {

	public DataPage<Associacao> findByFilter(AssociacaoFilterData filter, Page page);
	
	public DataPage<Associacao> zoom(Map<String, Object> params, Page page);
	
}
