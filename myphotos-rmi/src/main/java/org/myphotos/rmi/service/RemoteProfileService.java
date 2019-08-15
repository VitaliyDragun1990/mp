package org.myphotos.rmi.service;


import javax.ejb.Remote;

import org.myphotos.domain.entity.Profile;
import org.myphotos.infra.exception.business.ObjectNotFoundException;
import org.myphotos.rmi.model.RemoteProfile;

@Remote
public interface RemoteProfileService {

	Profile findById(Long id) throws ObjectNotFoundException;
	
	RemoteProfile findRemoteById(Long id) throws ObjectNotFoundException;
}
