package com.mksdev.transport.facade.impl;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import com.mksdev.framework.base.dao.exception.DaoException;
import com.mksdev.framework.base.facade.impl.CrudFacadeImpl;
import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.transport.dao.InstituicaoDAO;
import com.mksdev.transport.entity.Instituicao;
import com.mksdev.transport.entity.QInstituicao;
import com.mksdev.transport.facade.InstituicaoFacade;
import com.mksdev.transport.facade.TransportadorFacade;
import com.mksdev.transport.filter.InstituicaoFilterData;

@Named
public class InstituicaoFacadeImpl extends CrudFacadeImpl<Long, Instituicao, QInstituicao> implements InstituicaoFacade {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private TransportadorFacade transportadorFacade;
	
	//Constructor with inject DAO
	@Inject
	public InstituicaoFacadeImpl(InstituicaoDAO dao) {
		super(dao);
	}

	@Override
	public DataPage<Instituicao> findByFilter(InstituicaoFilterData filter, Page page) {
		return ((InstituicaoDAO) super.getDAO()).findByFilterQ(filter, page);
	}
	
	@Override
	public Instituicao findByFilter(InstituicaoFilterData filter) {
		return ((InstituicaoDAO) super.getDAO()).findByFilterQ(filter);
	}
	
	@Override
	public List<Instituicao> findAll() {
		return ((InstituicaoDAO) super.getDAO()).findAll();
	}
	
	public Instituicao persist(Long userId, Instituicao instituicao) throws ValidationException, ConstraintViolationException, DaoException{
		instituicao.setTransportador(transportadorFacade.findByUser(userId));
		return super.persist(instituicao);
	}
	
	public Instituicao findById(Long id){
		return ((InstituicaoDAO) super.getDAO()).findByIdQ(id);
	}
	
	public DataPage<Instituicao> zoom(InstituicaoFilterData filter, Page page){
		return ((InstituicaoDAO) super.getDAO()).zoomQ(filter, page);
	}
	
}