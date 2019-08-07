package org.myphotos.ws.service;

import javax.activation.DataHandler;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import org.myphotos.ws.model.ImageLinkSOAP;
import org.myphotos.ws.model.PhotosSOAP;

/**
 * http://soap.myphotos.com/ws/PhotoService?wsdl
 * http://soap.myphotos.com/ws/PhotoService?Tester
 * 
 * @author Vitaliy Dragun
 *
 */
@WebService(targetNamespace = "http://soap.myphotos.com/ws/PhotoService?wsdl")
public interface PhotoWebService {

	@WebMethod
	@WebResult(name = "photos")
	public PhotosSOAP findAllOrderByPhotoPopularity(
			@WebParam(name = "page") int page,
			@WebParam(name = "limit") int limit,
			@WebParam(name = "withTotal") boolean withTotal);
	
	@WebMethod
	@WebResult(name = "photos")
	public PhotosSOAP findAllOrderBAuthorPopularity(
			@WebParam(name = "page") int page,
			@WebParam(name = "limit") int limit,
			@WebParam(name = "withTotal") boolean withTotal);
	
	@WebMethod
	@WebResult(name = "imageLink")
	public ImageLinkSOAP viewLargePhoto(
			@WebParam(name = "photoId") Long photoId);
	
	@WebMethod
	@WebResult(name = "originalImage")
	public DataHandler downloadOriginalImage(
			@WebParam(name = "photoId") Long photoId);
}
