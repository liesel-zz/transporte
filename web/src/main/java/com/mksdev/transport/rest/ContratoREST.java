package com.mksdev.transport.rest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBException;
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
import com.mksdev.transport.entity.Contrato;
import com.mksdev.transport.facade.ContratoFacade;
import com.mksdev.transport.filter.ContratoFilterData;

/**
 * 
 * @version 0.0.1-SNAPSHOTs
 * @author Fernando <fernando@mksdev.com>
 * 
 */
@Path("/contrato")
@RequestScoped
@LazyAware

@Security
public class ContratoREST extends BaseREST {
	
	@Inject
	private Logger log;

	@Inject
	private ContratoFacade facade;

	@Inject
	private Validator validator;
	
	@GET
	@Path("/findById/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "Transporte" })
	public Contrato findById(@PathParam("id") Long id) {
		
		ContratoFilterData filter = new ContratoFilterData(this.getLoggedUser().getId(), id);
		filter.setFetchCliente(true);
		
		Contrato contrato = facade.findByFilter(filter);
		
		if (contrato == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return contrato;
	}

	@GET
	@Path("/findByFilter")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "Transporte" })
	public DataPage<Contrato> findByFilter( @QueryParam("ano") int ano,
											// default page fields
											@QueryParam("startRow") int startRow,
											@QueryParam("pageSize") int pageSize) {
		
		ContratoFilterData filter = new ContratoFilterData(this.getLoggedUser().getId());
		filter.setFetchCliente(true);
		
		filter.setAno(ano);
		
		return facade.findByFilter(filter, new Page((startRow - 1) * pageSize, pageSize));
	}

	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response save(Contrato contrato) {
		Response.ResponseBuilder builder = null;
		try {
			// NOVO RESETA ID
			contrato.setId(null);

			validate(contrato,false);
			facade.persist(contrato);
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
		} catch (EJBException e) {
			if (e.getCausedByException() instanceof ConstraintViolationException) {
				ConstraintViolationException ce = (ConstraintViolationException) e
						.getCausedByException();
				builder = createViolationResponse(ce.getConstraintViolations());
			} else {
			}

		} catch (Exception e) {
			System.out.println(e);
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
	@RolesAllowed({ "Transporte" })
	public Response update(@PathParam("id") Long id, Contrato contrato) {
		Response.ResponseBuilder builder = null;
		try {
			validate(contrato,true);
			facade.merge(contrato);
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
		} catch (EJBException e) {
			if (e.getCausedByException() instanceof ConstraintViolationException) {
				ConstraintViolationException ce = (ConstraintViolationException) e
						.getCausedByException();
				builder = createViolationResponse(ce.getConstraintViolations());
			} else {
			}
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
	@RolesAllowed({ "Transporte" })
	public Response remove(@PathParam("id") Long id) {
		Response.ResponseBuilder builder = null;
		try {
			Contrato contrato = facade.findById(id);
			facade.remove(contrato);
			// Create an "ok" response
			builder = Response.ok();
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
	private void validate(Contrato contrato, boolean update) throws ConstraintViolationException,
			ValidationException {
		// Create a bean validator and check for issues.
		Set<ConstraintViolation<Contrato>> violations = validator.validate(contrato);
		
		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(
					new HashSet<ConstraintViolation<?>>(violations));
		}

	}
}
