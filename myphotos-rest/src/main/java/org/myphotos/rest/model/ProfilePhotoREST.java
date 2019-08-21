package org.myphotos.rest.model;

import javax.xml.bind.annotation.XmlType;

import org.myphotos.converter.ConvertAsAbsoluteURL;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@XmlType(name = "")
@ApiModel("ProfilePhoto")
public class ProfilePhotoREST {

	private Long id;
	private String smallUrl;
	private long views;
	private long downloads;

	@ApiModelProperty(required = true, value = "Id of the photo. Used as unique identifier for /preview and /download API")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@ApiModelProperty(required = true)
	@ConvertAsAbsoluteURL
	public String getSmallUrl() {
		return smallUrl;
	}

	public void setSmallUrl(String smallUrl) {
		this.smallUrl = smallUrl;
	}

	@ApiModelProperty(required = true)
	public long getViews() {
		return views;
	}

	public void setViews(long views) {
		this.views = views;
	}

	@ApiModelProperty(required = true)
	public long getDownloads() {
		return downloads;
	}

	public void setDownloads(long downloads) {
		this.downloads = downloads;
	}

}
