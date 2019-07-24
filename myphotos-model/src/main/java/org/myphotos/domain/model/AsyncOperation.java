package org.myphotos.domain.model;

/**
 * Represents asynchronous operation of some kind
 * 
 * @author Vitaliy Dragun
 *
 * @param <T> type of the result of asynchronous operation
 */
public interface AsyncOperation<T> {

	/**
	 * Returns timeout of this asynchronous operation
	 */
	long getTimeOutInMillis();

	/**
	 * Callback that will be fired in case this asynchronous operation succeeded
	 * 
	 * @param result result of the asynchronous operation
	 */
	void onSuccess(T result);

	/**
	 * Callback that will be fired in case this asynchronous operation failed
	 * 
	 * @param throwable throwable that caused operation fail
	 */
	void onFail(Throwable throwable);
}
