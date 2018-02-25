package org.indranil.webservice.messenger.exceptionMapper;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.indranil.webservice.messenger.exception.DataNotFoundException;
import org.indranil.webservice.messenger.model.ErrorMessage;

@Provider
public class DataNotFoundExceptionMapper implements ExceptionMapper<DataNotFoundException> {

	@Override
	public Response toResponse(DataNotFoundException ex) {
		ErrorMessage errorMessage = new ErrorMessage(ex.getMessage(), 404, "http://google.com");
		return Response.status(Status.NOT_FOUND)
				.entity(errorMessage)
				.build();
	}

}
