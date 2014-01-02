package com.mksdev.transport.test.security;

import java.util.concurrent.Callable;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RunAs;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.jboss.ejb3.annotation.RunAsPrincipal;

import com.mksdev.framework.security.facade.RoleFacade;

@Stateless
// CREATE FAKE LOGIN
@RunAs("Admin")
@RunAsPrincipal("test@mksdev.com")
public class FrameworkRoleBean {

	@Inject
	private RoleFacade facade;

	@PermitAll
	public RoleFacade getFacade() {
		return facade;
	}

	@PermitAll
	public <V> V call(final Callable<V> callable) throws Exception {
		return callable.call();
	}
}
