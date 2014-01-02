package com.mksdev.transport.dao;

import java.util.List;

import com.mksdev.framework.base.dao.CrudDAO;
import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.transport.entity.Instituicao;
import com.mksdev.transport.entity.QInstituicao;
import com.mksdev.transport.filter.InstituicaoFilterData;

public interface InstituicaoDAO extends CrudDAO<Long, Instituicao, QInstituicao> {
	
	public List<Instituicao> findAll();

	public Instituicao findByIdQ(Long id);
	
	public Instituicao findByFilterQ(InstituicaoFilterData filter);
	
	public DataPage<Instituicao> findByFilterQ(InstituicaoFilterData filter, Page page);
	
	public DataPage<Instituicao> zoomQ(InstituicaoFilterData filter, Page page);
	
}