package org.myphotos.config;

public final class JMSEnvironmentSettings {
	
	public static final String JMS_CONNECTION_FACTORY_JNDI_NAME = "jms/myphotos/JMSConnectionFactory";
	
	public static final String UPLOAD_REQUEST_QUEUE_JNDI_NAME = "jms/myphotos/UploadRequestQueue";
	
	public static final String UPLOAD_RESPONSE_QUEUE_JNDI_NAME = "jms/myphotos/UploadResponseQueue";

	private JMSEnvironmentSettings() {}
}
