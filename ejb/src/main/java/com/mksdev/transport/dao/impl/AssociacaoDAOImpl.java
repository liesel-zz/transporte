package com.mksdev.transport.dao.impl;

import java.util.Map;

import javax.ejb.Stateless;

import com.mksdev.framework.base.dao.impl.CrudDAOImpl;
import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.framework.base.security.Log;
import com.mksdev.transport.dao.AssociacaoDAO;
import com.mksdev.transport.entity.Associacao;
import com.mksdev.transport.entity.QAssociacao;
import com.mksdev.transport.filter.AssociacaoFilterData;

@Stateless
@Log
public class AssociacaoDAOImpl extends CrudDAOImpl<Long, Associacao, QAssociacao> implements AssociacaoDAO{
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public DataPage<Associacao> findByFilter(AssociacaoFilterData filterData, Page page) {
		return super.findListNamed("ASSOCIACAO.FIND_BY_FILTER", "ASSOCIACAO.COUNT_FIND_BY_FILTER", page, filterData.getFilterData());
	}
	
	@Override
	public DataPage<Associacao> zoom(Map<String, Object> params, Page page) {
		return super.findListNamed("ASSOCIACAO.ZOOM", "ASSOCIACAO.COUNT_ZOOM", page, params);
	}

}