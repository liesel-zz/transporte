package com.mksdev.transport.dao;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import com.mksdev.framework.base.dao.CrudDAO;
import com.mksdev.framework.base.dao.exception.DaoException;
import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.transport.entity.Estudante;
import com.mksdev.transport.entity.EstudanteEndereco;
import com.mksdev.transport.entity.QEstudante;
import com.mksdev.transport.filter.EstudanteFilterData;

public interface EstudanteDAO extends CrudDAO<Long, Estudante, QEstudante>{

	public List<Estudante> findAll();
	
	public DataPage<Estudante> findByFilterQ(EstudanteFilterData filterData, Page page);
	
	public Set<Estudante> findByCliente(EstudanteFilterData filter);
	
	public Estudante findByFilterQ(EstudanteFilterData filter);
	
	public void persist(Estudante estudante, List<EstudanteEndereco> enderecos) throws ValidationException, ConstraintViolationException, DaoException;
	
	public void merge(Estudante estudante, List<EstudanteEndereco> enderecos) throws ValidationException, ConstraintViolationException, DaoException;
}
