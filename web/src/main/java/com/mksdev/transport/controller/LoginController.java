package com.mksdev.transport.controller;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import com.mksdev.framework.base.dao.exception.DaoException;
import com.mksdev.framework.base.security.ExceptionAware;
import com.mksdev.framework.base.security.Log;
import com.mksdev.framework.base.security.Security;
import com.mksdev.framework.security.entity.User;
import com.mksdev.framework.security.facade.UserFacade;


@SessionScoped
@Named

@Security
@Log
@ExceptionAware
public class LoginController implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1;

	// CURRENT NEW USER
	private User activeItem;
	
	@Inject
	private UserFacade facade;
	
	@Inject
	private FacesContext facesContext;

	public User getActiveItem() {
		return activeItem;
	}

	public void setActiveItem(User activeItem) {
		this.activeItem = activeItem;
	}
	
	/**
	 * @throws DaoException
	 * 
	 * */
	public String persist() throws DaoException {
		// TRIAL
		getActiveItem().setStatus(1L);

		facade.save(getActiveItem());

		facesContext.addMessage(null, new FacesMessage(
				FacesMessage.SEVERITY_INFO, "Registered!",
				"Registration successful"));

		//clear();
		// refresh view
		return "/private/index/?faces-redirect=true";
	}
	
	/**
	 * Inializa a interface
	 * 
	 * */
	@PostConstruct
	public void initView() {
		activeItem = new User();
	}

}
