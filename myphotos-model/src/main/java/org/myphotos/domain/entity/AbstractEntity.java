package org.myphotos.domain.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.myphotos.converter.LocalDateTimeConverter;
import org.myphotos.infra.util.CommonUtils;
import org.myphotos.validation.PastTime;

@MappedSuperclass
public abstract class AbstractEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private LocalDateTime created;
	
	@NotNull
	@PastTime
	@Convert(converter = LocalDateTimeConverter.class)
	@Column(nullable = false, updatable = false)
	public LocalDateTime getCreated() {
		return created;
	}
	
	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
	
	@Override
	public String toString() {
		return CommonUtils.toString(this);
	}
}
