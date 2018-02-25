package org.indranil.webservice.messenger.resources;

import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.indranil.webservice.messenger.model.Comment;
import org.indranil.webservice.messenger.resources.beans.CommentFilterBean;
import org.indranil.webservice.messenger.service.CommentService;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)

public class CommentResource {

	private CommentService commentService = new CommentService();
	
	@GET
	public List<Comment> getAllComments(@PathParam("messageId") long messageId) {
		return commentService.getAllComments(messageId);
	}
	
	@POST
	public Comment addComment(@PathParam("messageId") long messageId, Comment comment) {
		return commentService.addComment(messageId, comment);
	}
	
	@PUT
	@Path("/{commentId}")
	public Comment updateComment(@BeanParam CommentFilterBean commentFilterBean, Comment comment) {
		comment.setId(commentFilterBean.getId());
		return commentService.updateComment(commentFilterBean.getMessageId(), comment);
	}
	
	@DELETE
	@Path("/{commentId}")
	public void deleteComment(@BeanParam CommentFilterBean commentFilterBean) {
		commentService.removeComment(commentFilterBean.getId(), commentFilterBean.getMessageId());
	}
	
	
	@GET
	@Path("/{commentId}")
	public Comment getMessage(@BeanParam CommentFilterBean commentFilterBean) {
		return commentService.getComment(commentFilterBean.getMessageId(), commentFilterBean.getId());
	}
	
}
