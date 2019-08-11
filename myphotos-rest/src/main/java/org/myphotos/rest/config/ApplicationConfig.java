package org.myphotos.rest.config;

import static org.myphotos.rest.Constants.CURRENT_VERSION;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import io.swagger.jaxrs.config.BeanConfig;

@ApplicationPath(CURRENT_VERSION)
public class ApplicationConfig extends Application {

	public ApplicationConfig() {
		BeanConfig beanConfig = new BeanConfig();
		
		beanConfig.setVersion("1.0");
		beanConfig.setSchemes(new String[] {"http"});
		beanConfig.setTitle("MyPhotos.com API (Version 1.0)");
		beanConfig.setBasePath(CURRENT_VERSION);
		beanConfig.setResourcePackage("org.myphotos.rest");
		beanConfig.setPrettyPrint(true);
		beanConfig.setLicense("http://www.apache.org/licenses/LICENSE-2.0");
		beanConfig.setScan(true);
	}
}
