package com.mksdev.transport.facade.impl;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import com.mksdev.framework.base.dao.exception.DaoException;
import com.mksdev.framework.base.facade.impl.CrudFacadeImpl;
import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.framework.base.security.LazyAware;
import com.mksdev.transport.dao.EstudanteDAO;
import com.mksdev.transport.dao.EstudanteEnderecoDAO;
import com.mksdev.transport.entity.Estudante;
import com.mksdev.transport.entity.EstudanteEndereco;
import com.mksdev.transport.entity.QEstudante;
import com.mksdev.transport.facade.EstudanteEnderecoFacade;
import com.mksdev.transport.facade.EstudanteFacade;
import com.mksdev.transport.filter.EstudanteFilterData;

@Named
@LazyAware
public class EstudanteFacadeImpl extends CrudFacadeImpl<Long, Estudante, QEstudante>
		implements EstudanteFacade {

	private static final long serialVersionUID = 1L;

	@Inject
	private EstudanteEnderecoDAO estudanteEnderecoDAO;
	
	@Inject
	private EstudanteEnderecoFacade estudanteEnderecoFacade;
	
	@Inject
	public EstudanteFacadeImpl(EstudanteDAO dao) {
		super(dao);
	}

	@Override
	public DataPage<Estudante> findByFilter(EstudanteFilterData filter, Page page) {
		return ((EstudanteDAO) super.getDAO()).findByFilterQ(filter, page);
	}
	
	public Set<Estudante> findByCliente(EstudanteFilterData filter) {
		return ((EstudanteDAO) super.getDAO()).findByCliente(filter);
	}
	
	@Override
	public List<Estudante> findAll() {
		return ((EstudanteDAO) super.getDAO()).findAll();
	}
	
	public Estudante findById(Long id){
		EstudanteFilterData filter = new EstudanteFilterData();
		filter.setId(id);
		
		return ((EstudanteDAO) super.getDAO()).findByFilterQ(filter);
	}
	
	public Estudante findByFilter(EstudanteFilterData filter){
		return ((EstudanteDAO) super.getDAO()).findByFilterQ(filter);
	}

	@Override
	public List<EstudanteEndereco> findAllEnderecoByEstudanteId(Long id) {
		return estudanteEnderecoDAO.findAllByEstudanteId(id);
	}
	
	public void remove(Estudante estudante)throws ValidationException, ConstraintViolationException, DaoException{
		estudanteEnderecoFacade.removeAllByEstudante(estudante.getId());
		super.remove(estudante);
	}
	
	public void persist(Estudante estudante, List<EstudanteEndereco> enderecos)throws ValidationException, ConstraintViolationException, DaoException{
		((EstudanteDAO) super.getDAO()).persist(estudante, enderecos);
	}
	
	public void merge(Estudante estudante, List<EstudanteEndereco> enderecos)throws ValidationException, ConstraintViolationException, DaoException{
		((EstudanteDAO) super.getDAO()).merge(estudante, enderecos);
	}
	
}
