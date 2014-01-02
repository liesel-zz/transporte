package com.mksdev.transport.rest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mksdev.framework.base.rest.BaseREST;
import com.mksdev.framework.base.security.LazyAware;
import com.mksdev.framework.base.security.Security;
import com.mksdev.framework.security.entity.User;
import com.mksdev.framework.security.facade.UserFacade;
import com.mksdev.transport.entity.Cliente;
import com.mksdev.transport.entity.Endereco;
import com.mksdev.transport.entity.Transportador;
import com.mksdev.transport.facade.TransportadorFacade;
import com.mksdev.transport.filter.TransportadorFilterData;

/**
 * 
 * @version 0.0.1-SNAPSHOTs
 * @author Fernando <fernando@mksdev.com>
 * 
 */
@Path("/transportador")
@RequestScoped
@LazyAware
@Security
public class TransportadorREST extends BaseREST {

	@Inject
	private TransportadorFacade facade;
	
	@Inject
	private UserFacade userFacade;
	
	@Inject
	private Validator validator;
	
	@Context
	private HttpServletRequest httpServletRequest;
	
	@GET
	@Path("/findByLoggedUser")
	@Produces(MediaType.APPLICATION_JSON)
	public Transportador findByLoggedUser() {
		
		TransportadorFilterData filter = new TransportadorFilterData();
		filter.setUserId(this.getLoggedUser().getId());
		filter.setFetchEndereco(true);
		filter.setFetchUsuario(true);
		filter.setFetchAssociacao(true);
		
		Transportador transportador = facade.findByFilter(filter);
		
		return transportador;
	}
	
	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response save(Transportador transportador) {
		Response.ResponseBuilder builder = null;
		try {
			// NOVO RESETA ID
			transportador.setId(null);
			transportador.setUsuario(userFacade.findById(this.getLoggedUser().getId()));
			
			validate(transportador, CREATE);
			facade.persist(transportador);
			// Create an "ok" response
			builder = Response.ok();
		} catch (ConstraintViolationException ce) {
			// Handle bean validation issues
			builder = createViolationResponse(ce.getConstraintViolations());
		} catch (ValidationException e) {
			// Handle the unique constrain violation
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("email", "Email taken");
			builder = Response.status(Response.Status.CONFLICT).entity(
					responseObj);
		} catch (Exception e) {

			System.out.println("TransportadorREST.createUser()" + e);

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
	public Response update(@PathParam("id") Long id, Transportador transportador) {
		Response.ResponseBuilder builder = null;
		try {
			validate(transportador, UPDATE);
			facade.merge(transportador);
			
			// Create an "ok" response
			builder = Response.ok();
		} catch (ConstraintViolationException ce) {
			// Handle bean validation issues
			builder = createViolationResponse(ce.getConstraintViolations());
		} catch (ValidationException e) {
			// Handle the unique constrain violation
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("email", "Email taken");
			builder = Response.status(Response.Status.CONFLICT).entity(
					responseObj);
		} catch (Exception e) {

			System.out.println("TransportadorREST.update()" + e);

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
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	public Response remove(@PathParam("id") Long id) {
		Response.ResponseBuilder builder = null;

		try {
			Transportador transportador = facade.findById(id);
			facade.remove(transportador);
			// Create an "ok" response
			builder = Response.ok();
		} catch (ConstraintViolationException ce) {
			// Handle bean validation issues
			builder = createViolationResponse(ce.getConstraintViolations());
		} catch (ValidationException e) {
			// Handle the unique constrain violation
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("email", "Email taken");
			builder = Response.status(Response.Status.CONFLICT).entity(
					responseObj);
		} catch (Exception e) {

			System.out.println("TransportadorREST.createUser()" + e);

			// Handle generic exceptions
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(
					responseObj);
		}

		return builder.build();
	}
	
	private void validate(Transportador transp, int operationType) throws ConstraintViolationException, ValidationException {
		
		if (operationType == CREATE || operationType == UPDATE) {
			Set<ConstraintViolation<Transportador>> violations = validator.validate(transp);
	
			if (!violations.isEmpty()) {
				throw new ConstraintViolationException(
						new HashSet<ConstraintViolation<?>>(violations));
			}
			
			Set<ConstraintViolation<Endereco>> violationsAddress = validator.validate(transp.getEndereco());
			
			if (!violationsAddress.isEmpty()) {
				throw new ConstraintViolationException(
						new HashSet<ConstraintViolation<?>>(violationsAddress));
			}
		}

	}


}
