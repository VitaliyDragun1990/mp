package org.myphotos.domain.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

import org.myphotos.converter.LocalDateTimeConverter;
import org.myphotos.infra.util.CommonUtils;
import org.myphotos.validation.PastTime;

@MappedSuperclass
public abstract class AbstractEntity<T extends Serializable> implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@NotNull
	@PastTime
	@Convert(converter = LocalDateTimeConverter.class)
	@Column(nullable = false)
	private LocalDateTime created;

	public abstract T getId();
	
	public LocalDateTime getCreated() {
		return created;
	}
	
	public void setCreated(LocalDateTime created) {
		this.created = created;
	}
	
	@PrePersist
	public void prePersist() {
		if (getId() == null) {
			setCreated(LocalDateTime.now());
		}
	}
	
	@Override
	public int hashCode() {
		throw new UnsupportedOperationException();
	}

	@Override
	public boolean equals(Object obj) {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public String toString() {
		return CommonUtils.toString(this);
	}
}
