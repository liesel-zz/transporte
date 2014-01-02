package com.mksdev.transport.dao;

import java.util.Date;
import java.util.List;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import com.mksdev.framework.base.dao.CrudDAO;
import com.mksdev.framework.base.dao.exception.DaoException;
import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.transport.entity.Pagamento;
import com.mksdev.transport.entity.QPagamento;
import com.mksdev.transport.filter.PagamentoFilterData;


public interface PagamentoDAO extends CrudDAO<Long, Pagamento, QPagamento> {
	
	public List<Pagamento> findAll();

	public Pagamento findByIdQ(PagamentoFilterData filter);
	
	public List<Pagamento> findByIdsQ(Long userId, Long[] ids);

	public DataPage<Pagamento> findByFilterQ(PagamentoFilterData filter, Page page);
	
	public void createPagamentos(List<Pagamento> pagamentoList) throws ValidationException, ConstraintViolationException, DaoException;
	
	public List<Pagamento> findByFilterQ(PagamentoFilterData filter);

	public List<Pagamento> findAllByVencimento(Date dtVencimento);
	
}
