package org.myphotos.ws.index;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.myphotos.infra.cdi.qualifier.Property;
import org.myphotos.ws.service.bean.PhotoWebServiceBean;
import org.myphotos.ws.service.bean.ProfileWebServiceBean;

@WebServlet("/index.html")
public class IndexServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Inject
	@Property("myphotos.host.soap.api")
	private String soapHost;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<WebServiceModel> webServices = getWebServiceModels(getWebServiceClasses());
		
		req.setAttribute("webservices", webServices);
		req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
	}

	private Class<?>[] getWebServiceClasses() {
		return new Class<?>[] {
			ProfileWebServiceBean.class,
			PhotoWebServiceBean.class
		};
	}
	
	private List<WebServiceModel> getWebServiceModels(Class<?>[] webServiceClasses) {
		List<WebServiceModel> list = new ArrayList<>();
		for (Class<?> clazz : webServiceClasses) {
			list.add(new WebServiceModel(clazz.getAnnotation(WebService.class), soapHost));
		}
		return list;
	}
	
	public static class WebServiceModel {
		private final String name;
		private final String port;
		private final String address;
		
		public WebServiceModel(WebService webService, String soapHost) {
			this.name = webService.serviceName();
			this.port = webService.portName();
			this.address = String.format("%s/%s", soapHost, webService.serviceName());
		}

		public String getName() {
			return name;
		}

		public String getPort() {
			return port;
		}

		public String getAddress() {
			return address;
		}
	}
}
