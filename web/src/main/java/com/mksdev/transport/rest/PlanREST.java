package com.mksdev.transport.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBException;
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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.framework.base.rest.BaseREST;
import com.mksdev.framework.base.security.LazyAware;
import com.mksdev.framework.base.security.Security;
import com.mksdev.framework.security.entity.Plan;
import com.mksdev.framework.security.facade.PlanFacade;
import com.mksdev.framework.security.filter.PlanFilterData;

/**
 * 
 * @version 0.0.1-SNAPSHOT
 * @author Fernando <fernando@mksdev.com>
 * 
 */
@Path("/plan")
@RequestScoped
@LazyAware

@Security
public class PlanREST extends BaseREST {

	@Inject
	private Logger log;

	@Inject
	private PlanFacade facade;

	@GET
	@Path("/findById/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "Admin" })
	public Plan findById(@PathParam("id") Long id) {
		Plan plan = facade.findById(id);
		if (plan == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}

		return plan;
	}

	@GET
	@Path("/findAll")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "Admin" })
	public List<Plan> findAll() {
		return facade.findAll();
	}

	@GET
	@Path("/findByFilter")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "Admin" })
	public DataPage<Plan> findByFilter(
			// filter
			@QueryParam("name") String name,
			// default page fields
			@QueryParam("startRow") int startRow,
			@QueryParam("pageSize") int pageSize) {
		return facade.findByFilter(new PlanFilterData(name), new Page(
				(startRow - 1) * pageSize, pageSize));
	}

	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "Admin" })
	public Response save(Plan plan) {
		Response.ResponseBuilder builder = null;
		try {
			// null id for hibernate make new
			plan.setId(null);
			
			facade.persist(plan);
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
	@RolesAllowed({ "Admin" })
	public Response update(@PathParam("id") Long id, Plan plan) {
		Response.ResponseBuilder builder = null;
		try {
			facade.merge(plan);
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
	@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "Admin" })
	public Response remove(@PathParam("id") Long id) {
		Response.ResponseBuilder builder = null;

		try {
			Plan plan = facade.findById(id);
			facade.remove(plan);
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

}
