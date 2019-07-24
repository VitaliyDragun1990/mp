package org.myphotos.infra.repository.inmemory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.Optional;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
class ProfileRepositoryInvocationHandler implements InvocationHandler {

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		if ("findByUid".equals(method.getName())) {
			String uid = String.valueOf(args[0]);
			if ("richard-hendricks".equals(uid)) {
				return Optional.of(InMemoryDataBase.PROFILE);
			} else {
				return Optional.empty();
			}
		}
		throw new UnsupportedOperationException(String.format("Method %s is not implemented yet", method));
	}

}
