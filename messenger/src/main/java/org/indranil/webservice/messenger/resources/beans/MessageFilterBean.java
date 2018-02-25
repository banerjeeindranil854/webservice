package org.indranil.webservice.messenger.resources.beans;

import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

public class MessageFilterBean {

	 private @QueryParam("year") int year;
	 private @QueryParam("start") int start;
	 private @QueryParam("size") int size;
	 private @Context UriInfo uriInfo;
	 private @PathParam("messageId") long id;
	
	 public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	public UriInfo getUriInfo() {
		return uriInfo;
	}
	public void setUriInfo(UriInfo uriInfo) {
		this.uriInfo = uriInfo;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	 
	 
	 
	
	
}
