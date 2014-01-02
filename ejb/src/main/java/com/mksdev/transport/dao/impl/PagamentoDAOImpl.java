package com.mksdev.transport.dao.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import com.mksdev.framework.base.dao.exception.DaoException;
import com.mksdev.framework.base.dao.impl.CrudDAOImpl;
import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.framework.base.security.Log;
import com.mksdev.transport.dao.PagamentoDAO;
import com.mksdev.transport.entity.Pagamento;
import com.mksdev.transport.entity.QPagamento;
import com.mksdev.transport.enums.SituacaoPagamentoEnum;
import com.mksdev.transport.filter.PagamentoFilterData;
import com.mysema.query.BooleanBuilder;

@Stateless
@Log
public class PagamentoDAOImpl extends CrudDAOImpl<Long, Pagamento, QPagamento> implements PagamentoDAO {

	private static final long serialVersionUID = 1L;
	
	QPagamento qPagamento = QPagamento.pagamento;
	
	@Override
	public DataPage<Pagamento> findByFilterQ(PagamentoFilterData filter, Page page) {
		return super.findQ(qPagamento, filter, page);
	}
	
	public Pagamento findByIdQ(PagamentoFilterData filter){
		return super.findSingleQ(qPagamento, filter.getParamsQ(qPagamento), filter.getJoinFetchQ(qPagamento));
	}
	
	public List<Pagamento> findByIdsQ(Long userId, Long[] ids){
		BooleanBuilder builder = new BooleanBuilder();
		for (Long id : ids) {
			builder.or(qPagamento.id.eq(id));
		}
		builder.and(qPagamento.contrato.cliente.transportador.usuario.id.eq(userId));
		
		return from(qPagamento).where(builder).orderBy(qPagamento.dtVencimento.asc()).list(qPagamento);
	}
	
	@Override
	public List<Pagamento> findAll() {
		return findNamed("PAGAMENTO.FIND_ALL");
	}
	
	public void createPagamentos(List<Pagamento> pagamentoList) throws ValidationException, ConstraintViolationException, DaoException {

		for (Pagamento pagamento : pagamentoList) {
			super.persist(pagamento);
		}
	}
	
	public List<Pagamento> findByFilterQ(PagamentoFilterData filter){
		return super.findQ(qPagamento, filter);
	}
	
	public List<Pagamento> findAllByVencimento(Date dtVencimento){
		BooleanBuilder builder = new BooleanBuilder();
		builder.and(qPagamento.dtVencimento.before(dtVencimento));
		builder.and(qPagamento.situacao.eq(SituacaoPagamentoEnum.Pendente));
		
		return from(qPagamento).where(builder).leftJoin(qPagamento.contrato).fetch().list(qPagamento);
	}
	
}
