package com.mksdev.transport.facade.impl;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Named;

import com.mksdev.framework.base.facade.impl.CrudFacadeImpl;
import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.transport.dao.AssociacaoDAO;
import com.mksdev.transport.entity.Associacao;
import com.mksdev.transport.entity.QAssociacao;
import com.mksdev.transport.facade.AssociacaoFacade;
import com.mksdev.transport.filter.AssociacaoFilterData;

@Named
public class AssociacaoFacadeImpl extends CrudFacadeImpl<Long, Associacao, QAssociacao> implements AssociacaoFacade {

	private static final long serialVersionUID = 1L;
	
	@Inject
	public AssociacaoFacadeImpl(AssociacaoDAO dao) {
		super(dao);
	}

	@Override
	public DataPage<Associacao> findByFilter(AssociacaoFilterData filter, Page page) {
		return ((AssociacaoDAO) super.getDAO()).findByFilter(filter, page);
	}
	
	@Override
	public DataPage<Associacao> zoom(Map<String, Object> param, Page page) {
		return ((AssociacaoDAO) super.getDAO()).zoom(param, page);
	}
}
