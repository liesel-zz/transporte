package com.mksdev.transport.facade.impl;

import javax.inject.Inject;
import javax.inject.Named;

import com.mksdev.framework.base.facade.impl.CrudFacadeImpl;
import com.mksdev.transport.dao.ModeloContratoDAO;
import com.mksdev.transport.entity.ModeloContrato;
import com.mksdev.transport.entity.QModeloContrato;
import com.mksdev.transport.facade.ModeloContratoFacade;

@Named
public class ModeloContratoFacadeImpl extends CrudFacadeImpl<Long, ModeloContrato, QModeloContrato> implements ModeloContratoFacade {

	private static final long serialVersionUID = 1L;
	
	@Inject
	public ModeloContratoFacadeImpl(ModeloContratoDAO dao) {
		super(dao);
	}
	
}
