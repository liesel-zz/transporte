package com.mksdev.transport.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import javax.ws.rs.Consumes;
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

import com.mksdev.framework.base.lazy.LazyToNull;
import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.framework.base.rest.BaseREST;
import com.mksdev.framework.base.security.LazyAware;
import com.mksdev.framework.base.security.Security;
import com.mksdev.transport.dto.EstudanteDTO;
import com.mksdev.transport.entity.Estudante;
import com.mksdev.transport.facade.EstudanteEnderecoFacade;
import com.mksdev.transport.facade.EstudanteFacade;
import com.mksdev.transport.filter.ClienteFilterData;
import com.mksdev.transport.filter.EstudanteFilterData;

@Path("/estudante")
@RequestScoped
@LazyAware
@Security
public class EstudanteREST extends BaseREST{

	@Inject
	private Logger log;

	@Inject
	private EstudanteFacade facade;
	
	@Inject
	private EstudanteEnderecoFacade estudanteEnderecoFacade;
	
	@Inject
	private LazyToNull lazyToNull;
	
	@GET
	@Path("/findById/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("Transporte")
	public EstudanteDTO findById(@PathParam("id") Long id) {
		
		log.info("findById");

		EstudanteFilterData filter = new EstudanteFilterData(this.getLoggedUser().getId(), id);
		filter.setFetchCliente(true);
		filter.setFetchInstituicoes(true);
		
		
		EstudanteDTO estudante = new EstudanteDTO();
		estudante.setEstudante(facade.findByFilter(filter));
		estudante.setEnderecos(estudanteEnderecoFacade.findAllEnderecoByEstudanteId(estudante.getEstudante().getId()));
		
		lazyToNull.setLazyToNull(estudante.getEstudante());
		lazyToNull.setLazyToNull(estudante.getEnderecos());
		
		return estudante;
	}

	@GET
	@Path("/findAll")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "Admin" })
	public List<Estudante> list() {
		return facade.findAll();
	}
	
	@GET
	@Path("/findByFilter")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("Transporte")
	public DataPage<Estudante> findByFilter(@QueryParam("nome") String nome,
										  // default page fields
										  @QueryParam("startRow") int startRow,
										  @QueryParam("pageSize") int pageSize) {
		
		EstudanteFilterData filter = new EstudanteFilterData(this.getLoggedUser().getId(), nome);
		filter.setOrderBy(EstudanteFilterData.BY_ID_ASC);

		return facade.findByFilter(filter, new Page(
				(startRow - 1) * pageSize, pageSize));
	}
	
	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("Transporte")
	public Response save(EstudanteDTO item) {
		Response.ResponseBuilder builder = null;
		try {
			
			// NOVO RESETA ID
			item.getEstudante().setId(null);
			
			facade.persist(item.getEstudante(), item.getEnderecos());
			
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
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("Transporte")
	public Response update(EstudanteDTO item) {
		Response.ResponseBuilder builder = null;
		try {
			
			facade.merge(item.getEstudante(), item.getEnderecos());

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
//	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed("Transporte")
	public Response remove(@PathParam("id") Long id) {
		Response.ResponseBuilder builder = null;

		try {
			Estudante item = facade.findById(id);
			facade.remove(item);
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

			// Handle generic exceptions
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(
					responseObj);
		}

		return builder.build();
	}

}

