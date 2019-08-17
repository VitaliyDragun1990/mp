package org.myphotos.ejb.repository.jpa.listener;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import javax.persistence.PrePersist;

import org.myphotos.domain.entity.AbstractEntity;

public class CreatedNowListener {

	@PrePersist
	public void setNow(AbstractEntity model) {
		model.setCreated(now());
	}

	/*
	 * LocalDateTime.now().minus(1, ChronoUnit.MILLIS) to satisfy @Past constraint
	 */
	private LocalDateTime now() {
		return LocalDateTime.now().minus(1, ChronoUnit.MILLIS);
	}
}
