package org.myphotos.transformer.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.myphotos.infra.util.CommonUtils;
import org.myphotos.transformer.Transformable;
import org.myphotos.transformer.Transformer;
import org.myphotos.transformer.exception.TransformerException;

/**
 * Default transformation engine that uses reflection to transform objects
 * 
 * @author Vitaly Dragun
 *
 */
@ApplicationScoped
class SimpleDTOTransformer implements Transformer {
	private Logger logger;
	private FieldProvider fieldProvider;
	
	@Inject
	public void setLogger(Logger logger) {
		this.logger = logger;
	}
	
	@Inject
	public void setFieldProvider(FieldProvider fieldProvider) {
		this.fieldProvider = fieldProvider;
	}

	@Override
	public <T, P extends Transformable<T>> P transform(final T entity, final Class<P> dtoClass) {
		checkParams(entity, dtoClass);
		
		return handleTransformation(entity, ReflectionUtils.createInstance(dtoClass));
	}

	@Override
	public <T, P extends Transformable<T>> List<P> transfrom(Iterable<T> entities, Class<P> dtoClass) {
		checkParams(entities, dtoClass);
		
		List<P> list = new ArrayList<>();
		for (T entity : entities) {
			P dto = transform(entity, dtoClass);
			list.add(dto);
		}
		return list;
	}

	@Override
	public <T, P extends Transformable<T>> void transform(T entity, P dto) {
		checkParam(entity != null, "Source transformation object is not initialized");
		checkParam(dto != null, "Destination transformation object is not initialized");

		handleTransformation(entity, dto);
	}

	@Override
	public <T, P extends Transformable<T>> T untransform(P dto, Class<T> entityClass) {
		checkParams(dto, entityClass);
		
		T entity = ReflectionUtils.createInstance(entityClass);
		List<String> similarFieldNames = ReflectionUtils.findSimilarFields(dto.getClass(), entityClass);
		ReflectionUtils.copyFields(dto, entity, similarFieldNames);
		
		dto.untransform(entity, this);
		
		if (logger.isLoggable(Level.FINE)) {
			logger.log(Level.FINE, "SimpleDTOTranmsformer.untransform: {0} DTO object,", toString(entity));
		}
		
		return entity;
	}

	@Override
	public <T, P extends Transformable<T>> List<T> untransfrom(Iterable<P> dtos, Class<T> entityClass) {
		checkParams(dtos, entityClass);
		
		List<T> list = new ArrayList<>();
		for (P dto : dtos) {
			T entity = untransform(dto, entityClass);
			list.add(entity);
		}
		return list;
	}
	
	private <T, P extends Transformable<T>> P handleTransformation(final T entity, final P dto) {
		List<String> similarFieldNames = fieldProvider.getSimilarFieldNames(entity.getClass(), dto.getClass());
		ReflectionUtils.copyFields(entity, dto, similarFieldNames);
		
		dto.transform(entity, this);
		
		if (logger.isLoggable(Level.FINE)) {
			logger.log(Level.FINE, "SimpleDTOTranmsformer.transform: {0} DTO object,", toString(dto));
		}
		
		return dto;
	}
	
	private static String toString(Object obj) {
		return CommonUtils.toString(obj);
	}
	
	private static void checkParams(Object source, Class<?> destClass) {
		checkParam(source != null, "Source object for transformation is not initialized");
		checkParam(destClass != null, "No target class is defined for transformation");
	}
	
	private static void checkParam(boolean condition, String msg) {
		if (!condition) {
			throw new TransformerException(msg);
		}
	}

}
