package org.indranil.webservice.messenger.resources;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.BeanParam;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.indranil.webservice.messenger.model.Message;
import org.indranil.webservice.messenger.resources.beans.MessageFilterBean;
import org.indranil.webservice.messenger.service.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {

	MessageService messageService = new MessageService();
	
	@GET
	public List<Message> getMessages(@BeanParam MessageFilterBean filterBean) {
		
		List<Message> allMessage=new ArrayList<>();
		if (filterBean.getYear() > 0) {
			return messageService.getAllMessagesForYear(filterBean.getYear());
		}
		if (filterBean.getStart() >= 0 && filterBean.getSize() > 0) {
			return messageService.getAllMessagesPaginated(filterBean.getStart(), filterBean.getSize());
		}
		for(Message message : messageService.getAllMessages()) {
		Message indMessage=addLinksToMessages(message,filterBean.getUriInfo());
		allMessage.add(indMessage);
		}
		return allMessage;
	}

	@POST
	public Response addMessage(Message message, @BeanParam MessageFilterBean filterBean) {
		Message newMessage=addLinksToMessages(messageService.addMessage(message),filterBean.getUriInfo());
		return Response.created(filterBean.getUriInfo().getAbsolutePathBuilder().path(String.valueOf(newMessage.getId())).build())
				.entity(newMessage)
				.build();
	}
	
	@PUT
	@Path("/{messageId}")
	public Response updateMessage(@BeanParam MessageFilterBean filterBean, Message message) {
		message.setId(filterBean.getId());
		Message updateMessage=addLinksToMessages(messageService.updateMessage(message),filterBean.getUriInfo());
		return Response.created(filterBean.getUriInfo().getAbsolutePathBuilder().path(String.valueOf(updateMessage.getId())).build())
		.entity(updateMessage)
		.build();
	}
	
	@DELETE
	@Path("/{messageId}")
	public Response deleteMessage(@BeanParam MessageFilterBean filterBean) {
		messageService.removeMessage(filterBean.getId());
		return Response.status(Status.OK).build();
	}
	
	
	@GET
	@Path("/{messageId}")
	public Response getMessage(@BeanParam MessageFilterBean filterBean) {
		Message message = addLinksToMessages(messageService.getMessage(filterBean.getId()),filterBean.getUriInfo());
		return Response.status(Status.FOUND)
				.entity(message)
				.build();
		
	}

	private String getUriForComments(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getBaseUriBuilder()
				.path(MessageResource.class)
	       		.path(MessageResource.class, "getCommentResource")
	       		.path(CommentResource.class)
	       		.resolveTemplate("messageId", message.getId())
	            .build();
	    return uri.toString();
	}

	private String getUriForProfile(UriInfo uriInfo, Message message) {
		URI uri = uriInfo.getBaseUriBuilder()
       		 .path(ProfileResource.class)
       		 .path(message.getAuthor())
             .build();
        return uri.toString();
	}

	private String getUriForSelf(UriInfo uriInfo, Message message) {
		String uri = uriInfo.getBaseUriBuilder()
		 .path(MessageResource.class)
		 .path(Long.toString(message.getId()))
		 .build()
		 .toString();
		return uri;
	}
	
	private Message addLinksToMessages(Message message,UriInfo uriInfo) {
		Message indMessages=new Message(message);
		indMessages.addLink(getUriForSelf(uriInfo, message), "self");
		indMessages.addLink(getUriForProfile(uriInfo, message), "profile");
		indMessages.addLink(getUriForComments(uriInfo, message), "comments");
		return indMessages;
	}
	
	
	
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}
	
}
