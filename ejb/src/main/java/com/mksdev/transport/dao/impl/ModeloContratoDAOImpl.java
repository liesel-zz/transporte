package com.mksdev.transport.dao.impl;

import javax.ejb.Stateless;

import com.mksdev.framework.base.dao.impl.CrudDAOImpl;
import com.mksdev.framework.base.security.Log;
import com.mksdev.transport.dao.ModeloContratoDAO;
import com.mksdev.transport.entity.ModeloContrato;
import com.mksdev.transport.entity.QModeloContrato;

@Stateless
@Log
public class ModeloContratoDAOImpl  extends CrudDAOImpl<Long, ModeloContrato, QModeloContrato> implements ModeloContratoDAO {

	private static final long serialVersionUID = 1L;

}
