package org.myphotos.web.listener;

import java.util.logging.Logger;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.myphotos.infra.cdi.qualifier.Property;

@WebListener
public class ApplicationListener implements ServletContextListener {
	
	@Inject
	private Logger logger;
	
	@Inject
	@Property("myphotos.social.google-plus.client-id")
	private String googlePlusClientId;

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		sce.getServletContext().setAttribute("googlePlusClientId", googlePlusClientId);
		logger.info("Application 'myphotos' initialized");
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		logger.info("Application 'myphotos' destroyed");
	}

}
