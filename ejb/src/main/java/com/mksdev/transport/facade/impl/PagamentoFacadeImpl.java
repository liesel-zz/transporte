package com.mksdev.transport.facade.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import com.mksdev.framework.base.dao.exception.DaoException;
import com.mksdev.framework.base.facade.impl.CrudFacadeImpl;
import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.transport.dao.PagamentoDAO;
import com.mksdev.transport.entity.Contrato;
import com.mksdev.transport.entity.Pagamento;
import com.mksdev.transport.entity.QPagamento;
import com.mksdev.transport.enums.SituacaoContratoEnum;
import com.mksdev.transport.enums.SituacaoPagamentoEnum;
import com.mksdev.transport.facade.ContratoFacade;
import com.mksdev.transport.facade.PagamentoFacade;
import com.mksdev.transport.filter.PagamentoFilterData;

public class PagamentoFacadeImpl extends CrudFacadeImpl<Long, Pagamento, QPagamento> implements PagamentoFacade {
	
	private static final long serialVersionUID = 1L;
	
	@Inject
	private ContratoFacade contratoFacade;
	
	@Inject
	public PagamentoFacadeImpl(PagamentoDAO dao) {
		super(dao);
	}
	
	@Override
	public DataPage<Pagamento> findByFilter(PagamentoFilterData filter, Page page) {
		return ((PagamentoDAO) super.getDAO()).findByFilterQ(filter, page);
	}
	
	@Override
	public List<Pagamento> findByFilter(PagamentoFilterData filter) {
		return ((PagamentoDAO) super.getDAO()).findByFilterQ(filter);
	}
	
	@Override
	public List<Pagamento> findAllByVencimento(Date dtVencimento){
		return ((PagamentoDAO) super.getDAO()).findAllByVencimento(dtVencimento);
	}

	@Override
	public List<Pagamento> findAll() {
		return ((PagamentoDAO) super.getDAO()).findAll();
	}
	
	public Pagamento findById(PagamentoFilterData filter){
		return ((PagamentoDAO) super.getDAO()).findByIdQ(filter);
	}
	
	public List<Pagamento> findByIds(Long userId, Long[] ids){
		return ((PagamentoDAO) super.getDAO()).findByIdsQ(userId, ids);
	}
	
	public void createByContrato(Long contratoId) throws ConstraintViolationException, ValidationException, DaoException{
		Contrato contrato = contratoFacade.findById(contratoId);
		
		Date today = new Date();
		Calendar contratoDt = new GregorianCalendar();
		contratoDt.setTimeInMillis(contrato.getDtIni().getTime());
		
		List<Pagamento> parcelaList = new ArrayList<Pagamento>();
		Pagamento pagamento;
		
		for (int i = 1; i <= contrato.getQtParcelas(); i++){
			contratoDt.set(Calendar.DAY_OF_MONTH, contrato.getDiaPagto());
			
			pagamento = new Pagamento();
			pagamento.setContrato(contrato);
			pagamento.setAno(contratoDt.get(Calendar.YEAR));
			pagamento.setMes(contratoDt.get(Calendar.MONTH) + 1);
			pagamento.setDtVencimento(contratoDt.getTime());
			pagamento.setSituacao(SituacaoPagamentoEnum.Pendente);
			pagamento.setValor(contrato.getVlParcelas());
			
			if (contratoDt.getTime().compareTo(today) < 0)
				pagamento.setSituacao(SituacaoPagamentoEnum.Atrasada);
			
			parcelaList.add(pagamento);
			
			contratoDt.set(Calendar.MONTH, contratoDt.get(Calendar.MONTH) + 1);
		}
		
		((PagamentoDAO) super.getDAO()).createPagamentos(parcelaList);
		
		this.updateSituacaoContrato(contratoId);
	}
	
	public Pagamento doPayment(Long id) throws ConstraintViolationException, ValidationException, DaoException{
		Pagamento pagamento = ((PagamentoDAO) super.getDAO()).findById(id);
		
		pagamento.setDtPagamento(new Date());
		pagamento.setSituacao(SituacaoPagamentoEnum.Paga);
		
		pagamento = ((PagamentoDAO) super.getDAO()).merge(pagamento);
		
		updateSituacaoContrato(pagamento.getContratoId());
		
		return pagamento;
	}
	
	public Pagamento dismissPayment(Long id) throws ConstraintViolationException, ValidationException, DaoException{
		Pagamento pagamento = ((PagamentoDAO) super.getDAO()).findById(id);
		
		pagamento.setDtPagamento(null);
		if(pagamento.getDtVencimento().before(new Date()))
			pagamento.setSituacao(SituacaoPagamentoEnum.Atrasada);
		else
			pagamento.setSituacao(SituacaoPagamentoEnum.Pendente);
		
		pagamento = ((PagamentoDAO) super.getDAO()).merge(pagamento);
		
		updateSituacaoContrato(pagamento.getContratoId());
		
		return pagamento;
	}
	
	public void removeByContrato(Long contratoId) throws ConstraintViolationException, ValidationException, DaoException{
		List<Pagamento> pagamentos = ((PagamentoDAO) super.getDAO()).findByFilterQ(new PagamentoFilterData(contratoId));
		
		for (Pagamento pagamento : pagamentos) {
			((PagamentoDAO) super.getDAO()).remove(pagamento);
		}
		
		updateSituacaoContrato(contratoId);
	}
	
	public void updateSituacaoContrato(Long id) throws ValidationException, ConstraintViolationException, DaoException {
		Contrato contrato = contratoFacade.findById(id);
		
		PagamentoFilterData filter = new PagamentoFilterData();
		filter.setContratoId(id);
		List<Pagamento> pagamentoLst = this.findByFilter(filter);
		
		if(pagamentoLst.isEmpty())
			contrato.setSituacao(SituacaoContratoEnum.Inexistente);
		else{
			contrato.setSituacao(SituacaoContratoEnum.Ok);
			for (Pagamento pagamento : pagamentoLst) {
				if(pagamento.getSituacao() == SituacaoPagamentoEnum.Atrasada)
					contrato.setSituacao(SituacaoContratoEnum.Atrasado);
			}
			
			if(contrato.getSituacao() == SituacaoContratoEnum.Ok && contrato.getDtFim().compareTo(new Date()) < 0)
				contrato.setSituacao(SituacaoContratoEnum.Encerrado);
			
		}
		
		contratoFacade.merge(contrato);
				
	}
}
