package org.myphotos.ejb.repository.inmemory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.Vetoed;
import javax.inject.Inject;

import org.myphotos.infra.repository.AccessTokenRepository;
import org.myphotos.infra.repository.PhotoRepository;
import org.myphotos.infra.repository.ProfileRepository;

@Dependent
public class InMemoryRepositoryFactory {

	@Inject
	private ProfileRepositoryInvocationHandler profileRepositoryInvocationHandler;
	
	@Inject
	private PhotoRepositoryInvocationHandler photoRepositoryInvocationHandler;
	
	@Produces
	@InMemorySource
	public ProfileRepository getProfileRepository() {
		return (ProfileRepository) Proxy.newProxyInstance(
				getClass().getClassLoader(),
				new Class[] {ProfileRepository.class},
				profileRepositoryInvocationHandler);
	}
	
	@Produces
	@InMemorySource
	public PhotoRepository getPhotoRepository() {
		return (PhotoRepository) Proxy.newProxyInstance(
				getClass().getClassLoader(),
				new Class[] {PhotoRepository.class},
				photoRepositoryInvocationHandler);

	}
	
	@Produces
	@InMemorySource
	public AccessTokenRepository getAccessTokenRepository() {
		return (AccessTokenRepository) Proxy.newProxyInstance(
				getClass().getClassLoader(),
				new Class[] {AccessTokenRepository.class},
				new UnsupportedexceptionInvocationHandler());
	}
	
	@Vetoed
	private static class UnsupportedexceptionInvocationHandler implements InvocationHandler {

		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			throw new UnsupportedOperationException("Not implemented yet.");
		}
		
	}
}
