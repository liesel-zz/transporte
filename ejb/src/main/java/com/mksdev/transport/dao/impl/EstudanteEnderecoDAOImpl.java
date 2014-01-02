package com.mksdev.transport.dao.impl;

import java.util.List;

import com.mksdev.framework.base.dao.impl.CrudDAOImpl;
import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.transport.dao.EstudanteEnderecoDAO;
import com.mksdev.transport.entity.EstudanteEndereco;
import com.mksdev.transport.entity.QEstudanteEndereco;
import com.mksdev.transport.filter.EstudanteEnderecoFilterData;

public class EstudanteEnderecoDAOImpl extends CrudDAOImpl<Long, EstudanteEndereco, QEstudanteEndereco> implements EstudanteEnderecoDAO{

	private static final long serialVersionUID = 1L;
	
	QEstudanteEndereco qEstudanteEndereco = QEstudanteEndereco.estudanteEndereco;
	
	@Override
	public List<EstudanteEndereco> findAllByEstudanteId(Long id) {
		return from(qEstudanteEndereco).where(qEstudanteEndereco.estudante.id.eq(id))
				.leftJoin(qEstudanteEndereco.endereco).fetch()
				.leftJoin(qEstudanteEndereco.estudante).fetch()
				.list(qEstudanteEndereco);
	}
	
	@Override
	public DataPage<EstudanteEndereco> findByFilterQ(EstudanteEnderecoFilterData filter, Page page) {
		return super.findQ(qEstudanteEndereco, filter, page);
	}
	
	@Override
	public EstudanteEndereco findByFilterQ(EstudanteEnderecoFilterData filter){
		return super.findSingleQ(qEstudanteEndereco, filter);
	}

}
