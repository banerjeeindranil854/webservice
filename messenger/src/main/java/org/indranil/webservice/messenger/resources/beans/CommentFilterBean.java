package org.indranil.webservice.messenger.resources.beans;

import javax.ws.rs.PathParam;

public class CommentFilterBean {
	private @PathParam("messageId") long messageId;
	private @PathParam("commentId") long id;
	public long getMessageId() {
		return messageId;
	}
	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
}
