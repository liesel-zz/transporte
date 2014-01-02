package com.mksdev.transport.facade.impl;

import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import com.mksdev.framework.base.dao.exception.DaoException;
import com.mksdev.framework.base.facade.impl.CrudFacadeImpl;
import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.transport.dao.ContratoDAO;
import com.mksdev.transport.dao.PagamentoDAO;
import com.mksdev.transport.entity.Contrato;
import com.mksdev.transport.entity.Pagamento;
import com.mksdev.transport.entity.QContrato;
import com.mksdev.transport.enums.SituacaoContratoEnum;
import com.mksdev.transport.enums.SituacaoPagamentoEnum;
import com.mksdev.transport.facade.ContratoFacade;
import com.mksdev.transport.filter.ContratoFilterData;
import com.mksdev.transport.filter.PagamentoFilterData;

@Named
public class ContratoFacadeImpl extends CrudFacadeImpl<Long, Contrato, QContrato> implements
		ContratoFacade {

	private static final long serialVersionUID = 1L;

	@Inject
	private PagamentoDAO pagamentoDAO;
	
	@Inject
	public ContratoFacadeImpl(ContratoDAO dao) {
		super(dao);
	}
	
	@Override
	public Contrato findById(Long id){
		ContratoFilterData filter = new ContratoFilterData();
		filter.setFetchCliente(true);
		filter.setId(id);
		
		return ((ContratoDAO) super.getDAO()).findByIdQ(filter);
	}
	
	@Override
	public Contrato findByFilter(ContratoFilterData filter){
		return ((ContratoDAO) super.getDAO()).findByIdQ(filter);
	}

	@Override
	public DataPage<Contrato> findByFilter(ContratoFilterData filter, Page page) {
		return ((ContratoDAO) super.getDAO()).findByFilterQ(filter, page);
	}
	
	@Override
	public Contrato persist(Contrato contrato) throws ValidationException, ConstraintViolationException, DaoException {
		contrato.setSituacao(SituacaoContratoEnum.Inexistente);
		return super.persist(contrato);
	}

}