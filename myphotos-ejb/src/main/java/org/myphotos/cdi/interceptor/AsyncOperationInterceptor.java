package org.myphotos.cdi.interceptor;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.myphotos.domain.model.AsyncOperation;

/**
 * This interceptor intercepts calls to specified method which parameters
 * contain {@link AsyncOperation} callback and change such parameter to proxy
 * instance in order to prevent {@link AsyncOperation#onFail(Throwable)} from
 * throwing {@link RuntimeException}. This mechanism protects transaction in
 * progress, if any, from being rolled back by possible runtime exception.
 * 
 * @author Vitaliy Dragun
 *
 */
@Interceptor
public class AsyncOperationInterceptor {

	public Object aroundProcessImageResource(InvocationContext ic) throws Exception {
		replaceAsyncOperationByProxy(ic);
		return ic.proceed();
	}

	@SuppressWarnings("rawtypes")
	private void replaceAsyncOperationByProxy(InvocationContext ic) {
		Object[] params = ic.getParameters();
		for (int i = 0; i < params.length; i++) {
			if (params[i] instanceof AsyncOperation) {
				params[i] = new AsyncOperationProxy((AsyncOperation) params[i]);
			}
		}
		ic.setParameters(params);
	}

	@SuppressWarnings("rawtypes")
	private static class AsyncOperationProxy implements AsyncOperation {

		private final AsyncOperation originalAsyncOperation;

		private AsyncOperationProxy(AsyncOperation originalAsyncOperation) {
			this.originalAsyncOperation = originalAsyncOperation;
		}

		@Override
		public long getTimeOutInMillis() {
			return originalAsyncOperation.getTimeOutInMillis();
		}

		@SuppressWarnings("unchecked")
		@Override
		public void onSuccess(Object result) {
			originalAsyncOperation.onSuccess(result);
		}

		@Override
		public void onFail(Throwable throwable) {
			try {
				originalAsyncOperation.onFail(throwable);
			} catch (RuntimeException e) {
				Logger.getLogger(getClass().getName()).log(Level.SEVERE,
						"AsyncOperation.onFailed throws exception: " + e.getMessage(), e);
			}
		}
	}
}
