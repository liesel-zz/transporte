package com.mksdev.transport.facade;

import java.util.List;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import com.mksdev.framework.base.dao.exception.DaoException;
import com.mksdev.framework.base.facade.CrudFacade;
import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.transport.entity.Instituicao;
import com.mksdev.transport.entity.QInstituicao;
import com.mksdev.transport.filter.InstituicaoFilterData;

/**
 * 
 * */
public interface InstituicaoFacade extends CrudFacade<Long, Instituicao, QInstituicao> {
	
	public List<Instituicao> findAll();
	
	public DataPage<Instituicao> findByFilter(InstituicaoFilterData filter, Page page);
	
	public Instituicao findByFilter(InstituicaoFilterData filter);
	
	public DataPage<Instituicao> zoom(InstituicaoFilterData filter, Page page);
	
	public Instituicao findById(Long id);
	
	public Instituicao persist(Long userId, Instituicao instituicao) throws ValidationException, ConstraintViolationException, DaoException;
}
