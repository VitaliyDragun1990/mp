package org.myphotos.rest.model;

import javax.xml.bind.annotation.XmlType;

@XmlType(name = "")
public class UploadPhotoResultREST extends ImageLinkREST {

	private Long id;

	public UploadPhotoResultREST(Long id, String url) {
		super(url);
		this.id = id;
	}

	public UploadPhotoResultREST() {
	}

	Long getId() {
		return id;
	}

	void setId(Long id) {
		this.id = id;
	}

}
