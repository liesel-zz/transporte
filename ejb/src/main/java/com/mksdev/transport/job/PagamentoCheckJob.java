package com.mksdev.transport.job;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.security.RunAs;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.jboss.ejb3.annotation.RunAsPrincipal;
import org.jboss.ejb3.annotation.SecurityDomain;

import com.mksdev.framework.base.dao.exception.DaoException;
import com.mksdev.transport.entity.Pagamento;
import com.mksdev.transport.enums.SituacaoPagamentoEnum;
import com.mksdev.transport.facade.PagamentoFacade;
import com.mksdev.transport.filter.PagamentoFilterData;

/**
 * Job responsável por alterar a situação das parcelas de pagamento.
 * 
 */
@Stateless
// CREATE FAKE LOGIN
@RunAs("Admin")
@RunAsPrincipal("jobrunner@mksdev.com")
@SecurityDomain("SecurityRealm")
public class PagamentoCheckJob {
	
	@Inject
	private Logger log;

	@Inject
	private PagamentoFacade facade;

	/**
	 * Deleta Hash de troca de senha a mais de 24 horas no sistema.<br />
	 * @throws DaoException 
	 * @throws ValidationException 
	 * @throws ConstraintViolationException 
	 */
	@Schedule(hour = "0", minute = "1", persistent = false)
	public void runJOB() throws ConstraintViolationException, ValidationException, DaoException {
		List<Pagamento> lista = facade.findAllByVencimento(new Date());
		for (Pagamento pagamento: lista) {
			pagamento.setSituacao(SituacaoPagamentoEnum.Atrasada);
			facade.merge(pagamento);			
		}
	}
}