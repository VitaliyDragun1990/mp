package org.myphotos.infra.resource;

import java.io.IOException;
import java.io.InputStream;

import javax.enterprise.inject.Vetoed;

/**
 * Allows to get data in form of {@link InputStream} from some kind of resource
 * 
 * @author Vitaliy Dragun
 *
 */
@Vetoed
public interface ResourceLoader {

	/**
	 * Checks whether rresource with specified {@code resourceName} is supported by
	 * this {@link ResourceLoader} implementation
	 * 
	 * @param resourceName name of the resource to check
	 * @return {@code true} if resource with specified name is supported,
	 *         {@code false} otherwise
	 */
	boolean isSupport(String resourceName);

	/**
	 * Returns input stream from resource with {@code resoureName} name
	 * 
	 * @param resourceName name of the resource to get input stream from
	 * @return {@link InputStream} for resource with given name
	 * @throws IOException
	 */
	InputStream getInputStream(String resourceName) throws IOException;
}
