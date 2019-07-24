package org.myphotos.transformer;

/**
 * Any object that supports direct/backward transformation into some kind of
 * other object.
 * 
 * @author Vitaliy Dragun
 *
 */
public interface Transformable<T> {

	/**
	 * Transforms given object into current one
	 * 
	 * @param t object to transform from
	 */
	void transform(T t, Transformer transformer);
	
	/**
	 * Transform current object into given one
	 * 
	 * @param t object to transform to
	 * @return transformet object
	 */
	T untransform(T t, Transformer transformer);
}
