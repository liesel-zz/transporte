package com.mksdev.transport.rest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.framework.base.rest.BaseREST;
import com.mksdev.framework.base.security.LazyAware;
import com.mksdev.framework.base.security.Security;
import com.mksdev.transport.entity.Endereco;
import com.mksdev.transport.entity.Instituicao;
import com.mksdev.transport.facade.InstituicaoFacade;
import com.mksdev.transport.filter.InstituicaoFilterData;

@Path("/instituicao")
@RequestScoped
@LazyAware
@Security
public class InstituicaoREST extends BaseREST{

	@Inject
	private Logger log;

	@Inject
	private InstituicaoFacade facade;
	
	@Inject
	private Validator validator;
	
	@GET
	@Path("/findById/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "Admin" })
	public Instituicao findById(@PathParam("id") Long id) {
		log.info("lookupInstituicaoByName");

		InstituicaoFilterData filter = new InstituicaoFilterData(this.getLoggedUser().getId(), id);
		filter.setFetchEndereco(true);
		filter.setFetchTransportador(true);
		
		Instituicao item = facade.findByFilter(filter);

		if (item == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}

		return item;
	}

	@GET
	@Path("/findAll")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "Admin" })
	public List<Instituicao> list() {
		return facade.findAll();
	}
	
	@GET
	@Path("/findByFilter")
	@Produces(MediaType.APPLICATION_JSON)
	public DataPage<Instituicao> findByFilter(@QueryParam("nome") String nome,
										      // default page fields
										      @QueryParam("startRow") int startRow,
										      @QueryParam("pageSize") int pageSize) {

		return facade.findByFilter(new InstituicaoFilterData(this.getLoggedUser().getId(), nome), 
				                   new Page((startRow - 1) * pageSize, pageSize));
	}
	
	@GET
	@Path("/zoom")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("Transporte")
	public DataPage<Instituicao> zoom(@QueryParam("nome") String nome,
								    // default page fields
								    @QueryParam("startRow") int startRow,
								    @QueryParam("pageSize") int pageSize) {
		
		InstituicaoFilterData filter = new InstituicaoFilterData(this.getLoggedUser().getId(), nome);
		
		return facade.zoom(filter, new Page((startRow - 1) * pageSize, pageSize));
	}
	

	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response save(Instituicao item) {
		Response.ResponseBuilder builder = null;
		try {
			
			// NOVO RESETA ID
			item.setId(null);
			
			validate(item, CREATE);
			facade.persist(this.getLoggedUser().getId(), item);

			// Create an "ok" response
			builder = Response.ok();
		} catch (ConstraintViolationException ce) {
			// Handle bean validation issues
			builder = createViolationResponse(ce.getConstraintViolations());
		} catch (ValidationException e) {
			// Handle the unique constrain violation
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.CONFLICT).entity(
					responseObj);
		} catch (Exception e) {
			// Handle generic exceptions
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(
					responseObj);
		}
		
		return builder.build();
	}
	
	@PUT
	@POST
	@Path("/update/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(@PathParam("id") Long id, Instituicao item) {
		Response.ResponseBuilder builder = null;
		try {
			validate(item, UPDATE);
			facade.merge(item);
			// Create an "ok" response
			builder = Response.ok();
		} catch (ConstraintViolationException ce) {
			// Handle bean validation issues
			builder = createViolationResponse(ce.getConstraintViolations());
		} catch (ValidationException e) {
			// Handle the unique constrain violation
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.CONFLICT).entity(
					responseObj);
		} catch (Exception e) {

			// Handle generic exceptions
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(
					responseObj);
		}
		
		return builder.build();
	}

	@DELETE
	@Path("/remove/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response remove(@PathParam("id") Long id) {
		Response.ResponseBuilder builder = null;

		try {
			Instituicao item = facade.findById(id);
			facade.remove(item);
			// Create an "ok" response
			builder = Response.ok();
		} catch (ConstraintViolationException ce) {
			// Handle bean validation issues
			builder = createViolationResponse(ce.getConstraintViolations());
		} catch (ValidationException e) {
			// Handle the unique constrain violation
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.CONFLICT).entity(
					responseObj);
		} catch (Exception e) {

			// Handle generic exceptions
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(
					responseObj);
		}

		return builder.build();
	}
	
	/**
	 * 
	 * */
	private void validate(Instituicao instituicao, int operationType) throws ConstraintViolationException, ValidationException {
		
		if (operationType == CREATE || operationType == UPDATE) {
			Set<ConstraintViolation<Instituicao>> violations = validator.validate(instituicao);
	
			if (!violations.isEmpty()) {
				throw new ConstraintViolationException(
						new HashSet<ConstraintViolation<?>>(violations));
			}
			
			Set<ConstraintViolation<Endereco>> violationsAddress = validator.validate(instituicao.getEndereco());
			
			if (!violationsAddress.isEmpty()) {
				throw new ConstraintViolationException(
						new HashSet<ConstraintViolation<?>>(violationsAddress));
			}
		}

	}

}
