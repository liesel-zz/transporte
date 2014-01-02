package com.mksdev.transport.dao;

import java.util.Map;

import com.mksdev.framework.base.dao.CrudDAO;
import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.transport.entity.Associacao;
import com.mksdev.transport.entity.QAssociacao;
import com.mksdev.transport.filter.AssociacaoFilterData;

public interface AssociacaoDAO extends CrudDAO<Long, Associacao, QAssociacao> {

	public DataPage<Associacao> findByFilter(AssociacaoFilterData filter, Page page);
	
	public DataPage<Associacao> zoom(Map<String, Object> filter, Page page);
	
}
