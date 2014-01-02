package com.mksdev.transport.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.framework.base.rest.BaseREST;
import com.mksdev.framework.base.security.LazyAware;
import com.mksdev.framework.base.security.Security;
import com.mksdev.transport.entity.Associacao;
import com.mksdev.transport.facade.AssociacaoFacade;
import com.mksdev.transport.filter.AssociacaoFilterData;

@Path("/associacao")
@RequestScoped
@LazyAware
@Security
public class AssociacaoREST extends BaseREST {
	
	@Inject
	private Logger log;

	@Inject
	private AssociacaoFacade facade;

	@GET
	@Path("/findByFilter")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("Transporte")
	public DataPage<Associacao> findByFilter(@QueryParam("nomeAbrev") String nomeAbrev,
										  // default page fields
										  @QueryParam("startRow") int startRow,
										  @QueryParam("pageSize") int pageSize) {

		return facade.findByFilter(new AssociacaoFilterData(nomeAbrev), new Page(
				(startRow - 1) * pageSize, pageSize));
	}
	
	@GET
	@Path("/zoom")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("Transporte")
	public DataPage<Associacao> zoom(@QueryParam("nomeAbrev") String nomeAbrev,
										  // default page fields
										  @QueryParam("startRow") int startRow,
										  @QueryParam("pageSize") int pageSize) {

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("nomeAbrev", "%" + nomeAbrev + "%");
		
		return facade.zoom(param, new Page((startRow - 1) * pageSize, pageSize));
	}

}