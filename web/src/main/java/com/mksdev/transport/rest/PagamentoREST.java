package com.mksdev.transport.rest;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.framework.base.rest.BaseREST;
import com.mksdev.framework.base.security.LazyAware;
import com.mksdev.framework.base.security.Security;
import com.mksdev.transport.entity.Pagamento;
import com.mksdev.transport.facade.PagamentoFacade;
import com.mksdev.transport.filter.PagamentoFilterData;

@Path("/pagamento")
@RequestScoped
@LazyAware
@Security
public class PagamentoREST extends BaseREST {
	
	@Inject
	private Logger log;

	@Inject
	private PagamentoFacade facade;
	
	@GET
	@Path("/findByContrato")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("Transporte")
	public DataPage<Pagamento> findByContrato(@QueryParam("contratoId") Long contratoId,
										  // default page fields
										  @QueryParam("startRow") int startRow,
										  @QueryParam("pageSize") int pageSize) {
		
		PagamentoFilterData filter = new PagamentoFilterData(contratoId);
		filter.setFetchContrato(true);

		return facade.findByFilter(filter, new Page((startRow - 1) * pageSize, pageSize));
	}
	
	@POST
	@Path("/createByContrato")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("Transporte")
	public Response createByContrato(Long contratoId) {
		Response.ResponseBuilder builder = null;
		try {
			
			facade.createByContrato(contratoId);
			
			// Create an "ok" response
			builder = Response.ok();
		}  catch (ConstraintViolationException ce) {
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
	@Path("/doPayment/{id}")
//	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("Transporte")
	public Response doPayment(@PathParam("id") Long id) {
		Response.ResponseBuilder builder = null;
		try {
			facade.doPayment(id);
			
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
	@Path("/dismissPayment/{id}")
//	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("Transporte")
	public Response dismissPayment(@PathParam("id") Long id) {
		Response.ResponseBuilder builder = null;
		try {
			facade.dismissPayment(id);
			
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
	@Path("/removeByContrato/{id}")
//	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("Transporte")
	public Response removeByContrato(@PathParam("id") Long id) {
		Response.ResponseBuilder builder = null;

		try {
			facade.removeByContrato(id);
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
	
}
