package com.mksdev.transport.rest;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJBException;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.persistence.NoResultException;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.framework.base.rest.BaseREST;
import com.mksdev.framework.base.security.LazyAware;
import com.mksdev.framework.base.security.Security;
import com.mksdev.framework.security.entity.Role;
import com.mksdev.framework.security.entity.User;
import com.mksdev.framework.security.facade.UserFacade;
import com.mksdev.framework.security.filter.UserFilterData;
import com.mksdev.transport.dto.NewPasswordDTO;

/**
 * 
 * @version 0.0.1-SNAPSHOTs
 * @author Fernando <fernando@mksdev.com>
 * 
 */
@Path("/user")
@RequestScoped
@LazyAware
@Security
public class UserREST extends BaseREST {

	@Inject
	private Logger log;

	@Inject
	private UserFacade facade;

	@Inject
	private Validator validator;

	@Context
	private HttpServletRequest httpServletRequest;

	@GET
	@Path("/findLoggedUser/")
	@Produces(MediaType.APPLICATION_JSON)
	public User findLoggedUser() {
		User user = facade.findById(this.getLoggedUser().getId());
		if (user == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return user;
	}

	@GET
	@Path("/findById/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "Admin" })
	public User findById(@PathParam("id") Long id) {
		User user = facade.findById(id);
		if (user == null) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		return user;
	}

	@GET
	@Path("/findAll")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "Admin" })
	public List<User> list() {
		return facade.findAll();
	}

	@GET
	@Path("/findByFilter")
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "Admin" })
	public DataPage<User> findByFilter(
			// filter
			@QueryParam("name") String name,
			// default page fields
			@QueryParam("startRow") int startRow,
			@QueryParam("pageSize") int pageSize) {

		return facade.findByFilter(new UserFilterData(name), new Page(
				(startRow - 1) * pageSize, pageSize));
	}

	@POST
	@Path("/register")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response register(User user) {
		
		Response.ResponseBuilder builder = null;
		try {
			user.setId(null);
			user.setStatus(0L);
			// ROLE INICIAL
			HashSet<Role> roles = new HashSet<Role>();
			roles.add(new Role("Transporte"));
			roles.add(new Role("Enabled"));
			// FIXME ENVIAR EMAIL PARA CONFIRMAR INSCRIÇÃO NO SISTEMA
			user.setListRoles(roles);
			
			validate(user, false);

			facade.register(user);

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

			System.out.println("UserResourceRESTService.register()" + e);

			// Handle generic exceptions
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(
					responseObj);
		}

		return builder.build();
	}

	@POST
	@Path("/save")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "Admin" })
	public Response save(User user) {
		Response.ResponseBuilder builder = null;
		try {
			// NOVO RESETA ID
			user.setId(null);

			validate(user, false);
			facade.persist(user);
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

	@PUT
	@POST
	@Path("/update/{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@RolesAllowed({ "Admin" })
	public Response update(@PathParam("id") Long id, User user) {
		Response.ResponseBuilder builder = null;
		try {
			validate(user, true);
			facade.merge(user);
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
			User usuario = facade.findById(id);
			facade.remove(usuario);
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

	@POST
	@Path("/lostPassword")
	//@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response lostPassword(String email) {
		Response.ResponseBuilder builder = null;
		try {
			facade.lostPassword(email);
			builder = Response.ok();
		} catch (EJBException e) {
			if (e.getCausedByException() instanceof ConstraintViolationException) {
				ConstraintViolationException ce = (ConstraintViolationException) e
						.getCausedByException();
				builder = createViolationResponse(ce.getConstraintViolations());
			} 
		} catch (ValidationException e) {
			// Handle the unique constrain violation
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.CONFLICT).entity(
					responseObj);
		} catch (Exception e) {
			e.printStackTrace();
			// Handle generic exceptions
			Map<String, String> responseObj = new HashMap<String, String>();
			responseObj.put("error", e.getMessage());
			builder = Response.status(Response.Status.BAD_REQUEST).entity(
					responseObj);
		}
		return builder.build();
	}

	@POST
	@Path("/resetPassword")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response resetPassword(NewPasswordDTO newPasswordDTO) {
		Response.ResponseBuilder builder = null;
		try {
			facade.resetPassword(newPasswordDTO.getUuid(), newPasswordDTO.getNewPassword());
			builder = Response.ok();
		} catch (EJBException e) {
			e.printStackTrace();
			if (e.getCausedByException() instanceof ConstraintViolationException) {
				ConstraintViolationException ce = (ConstraintViolationException) e
						.getCausedByException();
				builder = createViolationResponse(ce.getConstraintViolations());
			} else {
			}
		} catch (Exception e) {
			e.printStackTrace();
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
	private void validate(User user, boolean update)
			throws ConstraintViolationException, ValidationException {
		// Create a bean validator and check for issues.
		Set<ConstraintViolation<User>> violations = validator.validate(user);

		if (!violations.isEmpty()) {
			throw new ConstraintViolationException(
					new HashSet<ConstraintViolation<?>>(violations));
		}

		// Check the uniqueness of the email address
		if (validateEmail(user.getEmail()) && !update) {
			throw new ValidationException("E-mail já registrado.");
		}
	}

	/**
	 * 
	 * */
	public boolean validateEmail(String email) {
		User user = null;
		try {
			user = facade.findByLogin(email);
		} catch (NoResultException e) {
		} catch (EJBException e) {
		}
		return user != null;
	}

}
