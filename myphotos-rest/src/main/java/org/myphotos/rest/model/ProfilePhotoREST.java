package org.myphotos.rest.model;

import javax.xml.bind.annotation.XmlType;

import org.myphotos.converter.ConvertAsAbsoluteURL;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlType(name = "")
@ApiModel("ProfilePhoto")
public class ProfilePhotoREST {

	@ApiModelProperty(required = true, value = "Id of the photo. Used as unique identifier for /preview and /download API")
	private Long id;

	@ConvertAsAbsoluteURL
	@ApiModelProperty(required = true)
	private String smallUrl;

	@ApiModelProperty(required = true)
	private long views;

	@ApiModelProperty(required = true)
	private long downloads;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getSmallUrl() {
		return smallUrl;
	}

	public void setSmallUrl(String smallUrl) {
		this.smallUrl = smallUrl;
	}

	public long getViews() {
		return views;
	}

	public void setViews(long views) {
		this.views = views;
	}

	public long getDownloads() {
		return downloads;
	}

	public void setDownloads(long downloads) {
		this.downloads = downloads;
	}

}
