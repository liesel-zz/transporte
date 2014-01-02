package com.mksdev.transport.job;

import java.util.List;
import java.util.logging.Logger;

import javax.annotation.security.RunAs;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.jboss.ejb3.annotation.RunAsPrincipal;
import org.jboss.ejb3.annotation.SecurityDomain;

import com.mksdev.framework.security.entity.User;
import com.mksdev.framework.security.facade.UserFacade;

/**
 * Exemplo de job.<br />
 * Este job lista os usuários do sistema a cada cinco minutos. Isto pode ser
 * configurado na anotação &#64;Schedule do método listUsers.
 */
@Stateless
// CREATE FAKE LOGIN
@RunAs("Admin")
@RunAsPrincipal("jobrunner@mksdev.com")
@SecurityDomain("SecurityRealm")
public class UserCheckJob {
	
	@Inject
	private Logger log;

	@Inject
	private UserFacade facade;

	/**
	 * Lista os usuários do sistema.<br />
	 * Permite configuração de agendamento na anotação &#64;Schedule.
	 */
	//@Schedule(hour = "*", minute = "*/30", persistent = false)
	public void listUsers() {
		List<User> lista = facade.findAll();
		log.info(">>>>>>>>> JOB: UserCheckJob.listUsers <<<<<<<<<");
		for (User user : lista) {
			log.info(user.toString());
		}
	}
}