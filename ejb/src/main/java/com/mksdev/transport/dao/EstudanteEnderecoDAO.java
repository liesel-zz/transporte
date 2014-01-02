package com.mksdev.transport.dao;

import java.util.List;

import com.mksdev.framework.base.dao.CrudDAO;
import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.transport.entity.EstudanteEndereco;
import com.mksdev.transport.entity.QEstudanteEndereco;
import com.mksdev.transport.filter.EstudanteEnderecoFilterData;

public interface EstudanteEnderecoDAO extends CrudDAO<Long, EstudanteEndereco, QEstudanteEndereco> {

	public List<EstudanteEndereco> findAllByEstudanteId(Long id);
		
	public DataPage<EstudanteEndereco> findByFilterQ(EstudanteEnderecoFilterData filterData, Page page);
	
	public EstudanteEndereco findByFilterQ(EstudanteEnderecoFilterData filter);
	
}
