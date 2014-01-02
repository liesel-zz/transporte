package com.mksdev.transport.dao.impl;

import java.util.List;

import javax.ejb.Stateless;

import com.mksdev.framework.base.dao.impl.CrudDAOImpl;
import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.framework.base.security.Log;
import com.mksdev.transport.dao.InstituicaoDAO;
import com.mksdev.transport.entity.Instituicao;
import com.mksdev.transport.entity.QInstituicao;
import com.mksdev.transport.filter.InstituicaoFilterData;

@Stateless
@Log
public class InstituicaoDAOImpl extends CrudDAOImpl<Long, Instituicao, QInstituicao> implements InstituicaoDAO {
	private static final long serialVersionUID = 1L;
	
	QInstituicao qInstituicao = QInstituicao.instituicao;
	
	@Override
	public DataPage<Instituicao> findByFilterQ(InstituicaoFilterData filter, Page page) {
		return super.findQ(qInstituicao, filter.getParamsQ(qInstituicao), page, filter.getJoinFetchQ(qInstituicao));
	}
	
	@Override
	public Instituicao findByFilterQ(InstituicaoFilterData filter) {
		return super.findSingleQ(qInstituicao, filter);
	}
	
	@Override
	public List<Instituicao> findAll() {
		return findNamed("INSTITUICAO.FIND_ALL");
	}
	
	public Instituicao findByIdQ(Long id){
		InstituicaoFilterData filter = new InstituicaoFilterData();
		filter.setId(id);
		return super.findSingleQ(qInstituicao, filter);
	}
	
	public DataPage<Instituicao> zoomQ(InstituicaoFilterData filter, Page page){
		return super.findQ(qInstituicao, filter, page);
	}
	
}
