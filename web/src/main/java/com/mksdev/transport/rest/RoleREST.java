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
import com.mksdev.framework.security.entity.Role;
import com.mksdev.framework.security.entity.User;
import com.mksdev.framework.security.facade.RoleFacade;
import com.mksdev.framework.security.filter.RoleFilterData;

/**
 * 
 * @version 0.0.1-SNAPSHOT
 * @author Fernando <fernando@mksdev.com>
 * 
 */
@Path("/role")
@RequestScoped
@LazyAware

@Security
public class RoleREST extends BaseREST {

	@Inject
	private Logger log;

	@Inject
	private RoleFacade facade;
	
	@Inject
	private Validator validator;

	@GET
	@Path("/findById/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "Admin" })
	public Role findById(@PathParam("id") String id) {
		System.out.println("RoleResourceRESTService.lookupById()");

		Role role = facade.findById(id);

		if (role == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}

		return role;
	}

	@GET
	@Path("/findAll")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "Admin" })
	public List<Role> findAll() {
		return facade.findAll();
	}

	@GET
	@Path("/findByFilter")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "Admin" })
	public DataPage<Role> findByFilter(
			// filter
			@QueryParam("role") String role,
			// default page fields
			@QueryParam("startRow") int startRow,
			@QueryParam("pageSize") int pageSize) {
		return facade.findByFilter(new RoleFilterData(role), new Page(
				(startRow - 1) * pageSize, pageSize));
	}

	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "Admin" })
	public Response save(Role role) {
		Response.ResponseBuilder builder = null;
		try {
			
			validate(role, false);
			
			facade.persist(role);
			// Create an "ok" response
			builder = Response.ok();
		} catch (ConstraintViolationException ce) {
			// Handle bean validation issues
			builder = createViolationResponse(ce.getConstraintViolations());
		} catch (ValidationException e) {
			// FIXME REFAZER

			// Handle the unique constrain violation
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("email", "Email taken");
			builder = Response.status(Response.Status.CONFLICT).entity(
					responseObj);
		} catch (Exception e) {

			System.out.println("UserResourceRESTService.createUser()" + e);

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
	@RolesAllowed({ "Admin" })
	public Response update(@PathParam("id") String id, Role role) {
		Response.ResponseBuilder builder = null;
		try {
			
			validate(role, true);
			
			facade.merge(role);
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

			System.out.println("UserResourceRESTService.update()" + e);

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
	//@Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "Admin" })
	public Response remove(@PathParam("id") String id) {
		Response.ResponseBuilder builder = null;

		try {
			Role role = facade.findById(id);
			facade.remove(role);
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

			System.out.println("UserResourceRESTService.createUser()" + e);

			// Handle generic exceptions
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(
					responseObj);
		}

		return builder.build();
	}
	
	private void validate(Role role, boolean update)
			throws ConstraintViolationException, ValidationException {
		// Create a bean validator and check for issues.
		Set<ConstraintViolation<Role>> violations = validator.validate(role);

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(
					new HashSet<ConstraintViolation<?>>(violations));
		}
	}

}
