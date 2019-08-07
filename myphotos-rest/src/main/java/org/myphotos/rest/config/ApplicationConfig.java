package org.myphotos.rest.config;

import static org.myphotos.rest.Constants.CURRENT_VERSION;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath(CURRENT_VERSION)
public class ApplicationConfig extends Application {

}
