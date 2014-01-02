package com.mksdev.transport.facade;

import java.util.Date;
import java.util.List;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import com.mksdev.framework.base.dao.exception.DaoException;
import com.mksdev.framework.base.facade.CrudFacade;
import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.transport.entity.Pagamento;
import com.mksdev.transport.entity.QPagamento;
import com.mksdev.transport.filter.PagamentoFilterData;

public interface PagamentoFacade extends CrudFacade<Long, Pagamento, QPagamento> {
	
	public List<Pagamento> findAll();
	
	public DataPage<Pagamento> findByFilter(PagamentoFilterData filter, Page page);
	
	public List<Pagamento> findByFilter(PagamentoFilterData filter);
	
	public Pagamento findById(PagamentoFilterData filter);
	
	public List<Pagamento> findAllByVencimento(Date dtVencimento);
	
	public List<Pagamento> findByIds(Long userId, Long[] ids);
	
	public void createByContrato(Long contratoId) throws ConstraintViolationException, ValidationException, DaoException;
	
	public Pagamento doPayment(Long id) throws ConstraintViolationException, ValidationException, DaoException;
	
	public Pagamento dismissPayment(Long id) throws ConstraintViolationException, ValidationException, DaoException;
	
	public void removeByContrato(Long contratoId) throws ValidationException, ConstraintViolationException, DaoException;
	
	public void updateSituacaoContrato(Long id) throws ValidationException, ConstraintViolationException, DaoException;
	
}
