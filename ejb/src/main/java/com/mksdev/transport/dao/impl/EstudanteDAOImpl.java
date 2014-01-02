package com.mksdev.transport.dao.impl;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import com.mksdev.framework.base.dao.exception.DaoException;
import com.mksdev.framework.base.dao.impl.CrudDAOImpl;
import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.framework.base.security.Log;
import com.mksdev.transport.dao.EstudanteDAO;
import com.mksdev.transport.dao.EstudanteEnderecoDAO;
import com.mksdev.transport.entity.Estudante;
import com.mksdev.transport.entity.EstudanteEndereco;
import com.mksdev.transport.entity.QEstudante;
import com.mksdev.transport.filter.EstudanteFilterData;

@Stateless
@Log
public class EstudanteDAOImpl extends CrudDAOImpl<Long, Estudante, QEstudante> implements EstudanteDAO{

	private static final long serialVersionUID = 1L;
	
	QEstudante qEstudante = QEstudante.estudante;
	
	@Inject
	private EstudanteEnderecoDAO estudanteEnderecoDAO;
	
	@Override
	public DataPage<Estudante> findByFilterQ(EstudanteFilterData filter, Page page) {
		return super.findQ(qEstudante, filter, page);
	}
	
	public Set<Estudante> findByCliente(EstudanteFilterData filter) {
		return new HashSet<Estudante>(super.findQ(qEstudante, filter.getParamsQ(qEstudante), filter.getJoinFetchQ(qEstudante)));
	}
	
	@Override
	public List<Estudante> findAll() {
		return findNamed("ESTUDANTE.FIND_ALL");
	}
	
	public Estudante findByFilterQ(EstudanteFilterData filter) {
		return super.findSingleQ(qEstudante, filter);		
	}
	
	public void persist(Estudante estudante, List<EstudanteEndereco> enderecos) throws ValidationException, ConstraintViolationException, DaoException {
		Estudante result = super.persist(estudante);
		saveEnderecos(result, enderecos);
	}
	
	public void merge(Estudante estudante, List<EstudanteEndereco> enderecos)throws ValidationException, ConstraintViolationException, DaoException{
		Estudante result = super.merge(estudante);
		saveEnderecos(result, enderecos);
	}
	
	private void saveEnderecos(Estudante estudante, List<EstudanteEndereco> enderecos)throws ValidationException, ConstraintViolationException, DaoException{
		
		for (EstudanteEndereco estudanteEndereco : enderecos) {
			estudanteEndereco.setEstudante(estudante);
			if (estudanteEndereco.entityIsNew())
				estudanteEnderecoDAO.persist(estudanteEndereco);
			else
				estudanteEnderecoDAO.merge(estudanteEndereco);
		}
		
	}
}
