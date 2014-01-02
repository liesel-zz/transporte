package com.mksdev.transport.facade;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;



import com.mksdev.framework.base.dao.exception.DaoException;
import com.mksdev.framework.base.facade.CrudFacade;
import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.transport.entity.Estudante;
import com.mksdev.transport.entity.EstudanteEndereco;
import com.mksdev.transport.entity.QEstudante;
import com.mksdev.transport.filter.EstudanteFilterData;

public interface EstudanteFacade extends CrudFacade<Long, Estudante, QEstudante> {

	public List<Estudante> findAll();
	
	public DataPage<Estudante> findByFilter(EstudanteFilterData filterData, Page page);
	
	public Set<Estudante> findByCliente(EstudanteFilterData filter);
	
	public Estudante findByFilter(EstudanteFilterData filter);
	
	public Estudante findById(Long id);
	
	public List<EstudanteEndereco> findAllEnderecoByEstudanteId(Long id);
	
	public void remove(Estudante estudante)throws ValidationException, ConstraintViolationException, DaoException;
	
	public void persist(Estudante estudante, List<EstudanteEndereco> enderecos)throws ValidationException, ConstraintViolationException, DaoException;
	
	public void merge(Estudante estudante, List<EstudanteEndereco> enderecos)throws ValidationException, ConstraintViolationException, DaoException;
}
