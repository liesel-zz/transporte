package com.mksdev.transport.test;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.logging.Logger;

import javax.inject.Inject;

import junit.framework.Assert;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.security.auth.spi.Util;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.mksdev.framework.base.dao.CrudDAO;
import com.mksdev.framework.base.dao.exception.DaoException;
import com.mksdev.framework.base.dao.impl.CrudDAOImpl;
import com.mksdev.framework.base.entity.BaseEntity;
import com.mksdev.framework.base.entity.impl.BaseEntityImpl;
import com.mksdev.framework.base.entity.impl.LogBaseEntityImpl;
import com.mksdev.framework.base.facade.CrudFacade;
import com.mksdev.framework.base.facade.impl.CrudFacadeImpl;
import com.mksdev.framework.base.filter.FilterData;
import com.mksdev.framework.base.mail.Mail;
import com.mksdev.framework.base.mail.impl.MailImpl;
import com.mksdev.framework.base.paging.DataPage;
import com.mksdev.framework.base.paging.Page;
import com.mksdev.framework.base.resources.Resources;
import com.mksdev.framework.security.dao.PasswordResetDAO;
import com.mksdev.framework.security.dao.UserDAO;
import com.mksdev.framework.security.dao.impl.PasswordResetDAOImpl;
import com.mksdev.framework.security.dao.impl.UserDAOImpl;
import com.mksdev.framework.security.entity.PasswordReset;
import com.mksdev.framework.security.entity.Plan;
import com.mksdev.framework.security.entity.Role;
import com.mksdev.framework.security.entity.User;
import com.mksdev.framework.security.facade.PasswordResetFacade;
import com.mksdev.framework.security.facade.UserFacade;
import com.mksdev.framework.security.facade.impl.PasswordResetFacadeImpl;
import com.mksdev.framework.security.facade.impl.UserFacadeImpl;
import com.mksdev.framework.security.filter.UserFilterData;
import com.mksdev.transport.test.security.FrameworkUserBean;

/**
 * 
 * Testa a classe de Users do framework
 * 
 * @author Fernando <fernando@mksdev.com>
 * @version 0.0.1-SNAPSHOT
 * 
 * */
@RunWith(Arquillian.class)
public class FrameworkUserTest {

	@Inject
	private Logger log;

	@Inject
	private FrameworkUserBean userBean;

	/**
	 * Create a test.war file for deploy in JBoss
	 * 
	 * @return Archive<?>
	 * 
	 * */
	@Deployment
	public static Archive<?> createTestArchive() {
		return ShrinkWrap
				.create(WebArchive.class, "test.war")
				.addClasses(FrameworkUserBean.class)
				.addClasses(User.class)
				.addClasses(Plan.class)
				.addClasses(Role.class)
				.addClasses(PasswordReset.class)
				
				.addClasses(UserFacade.class)
				.addClasses(UserFacadeImpl.class)
				.addClasses(UserDAO.class)
				.addClasses(UserDAOImpl.class)
				.addClasses(UserFilterData.class)
				.addClass(PasswordResetFacade.class)
				.addClass(PasswordResetFacadeImpl.class)
				.addClass(PasswordResetDAO.class)
				.addClass(PasswordResetDAOImpl.class)
				
				// Framewrok Class
				.addClasses(CrudFacade.class)
				.addClasses(CrudFacadeImpl.class)
				.addClasses(CrudDAO.class)
				.addClasses(CrudDAOImpl.class)
				.addClasses(DataPage.class)
				.addClasses(Page.class)
				.addClasses(DaoException.class)
				.addClasses(LogBaseEntityImpl.class)
				.addClasses(BaseEntityImpl.class)
				.addClasses(BaseEntity.class)
				.addClasses(FilterData.class)
				.addClasses(Mail.class)
				.addClasses(MailImpl.class)
				.addClasses(Util.class)
				
				// QUERYDSL
				.addPackage("com.mysema.query")
				.addPackage("com.mysema.query.sql")
				.addPackage("com.mysema.query.jpa")
				.addPackage("com.mysema.query.jpa.impl")
				.addPackage("com.mysema.query.types")
				.addPackage("com.mysema.query.support")
				.addPackage("com.mysema.query.types.path")
				.addPackage("com.mysema.query.types.expr")
				.addPackage("com.mysema.query.dml")
				.addPackage("com.mysema.commons.lang")
				.addPackage("com.google.common.base")
				.addPackage("com.google.common.collect")
				
				
				.addClasses(Resources.class)
				.addAsResource("META-INF/test-persistence.xml",
						"META-INF/persistence.xml")
				.addAsWebInfResource("test-jboss-web.xml", "jboss-web.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				// Deploy our test datasource
				.addAsWebInfResource("test-ds.xml", "test-ds.xml");
	}

	//@Test
	//@InSequence(1)
	public void testPersist() throws Exception {
		userBean.call(new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				User entity = getEntity();
				entity = userBean.getFacade().persist(entity);
				Assert.assertNotNull(entity.getId());
				log.info(entity.getName() + " was persisted with id "
						+ entity.getId());
				return null;
			}

		});
	}

	//@Test
	//@InSequence(2)
	public void testMerge() throws Exception {
		userBean.call(new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				User entity = getEntity();
				entity = userBean.getFacade().findByLogin(entity.getEmail());
				Assert.assertNotNull(entity.getId());
				log.info(entity.getName() + " was persisted with id "
						+ entity.getId());
				return null;
			}

		});
	}
	
	@Test
	public void testLostPassword() throws Exception {
		userBean.call(new Callable<Object>() {
			@Override
			public Object call() throws Exception {
				userBean.getFacade().lostPassword("fernando@mksdev.com");
				return null;
			}
		});
	}

	/**
	 * Get Entity For Test
	 * */
	private User getEntity() {
		User entity = new User();
		entity.setId(null);
		entity.setName("Teste Unitario");
		entity.setEmail("teste@mksdev.com");
		entity.setPassword("teste");
		entity.setBirthDay(new Date());
		entity.setLastLogin(new Date());
		entity.setStatus(1L);

		Set<Role> roles = new HashSet<Role>();
		Role roleTransporte = new Role();
		roleTransporte.setRole("Transporte");
		roleTransporte.setDescription("Transporte Role");
		roles.add(roleTransporte);

		Role roleAdmin = new Role();
		roleAdmin.setRole("Admin");
		roleAdmin.setDescription("Admin Role");
		roles.add(roleAdmin);

		Role roleAuthenticated = new Role();
		roleAuthenticated.setId("Enabled");
		roleAuthenticated.setDescription("Enabled Role");
		roles.add(roleAuthenticated);

		entity.setListRoles(roles);

		return entity;
	}

}
