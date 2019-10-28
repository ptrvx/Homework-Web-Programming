package coupons.exceptions;

import coupons.responses.ErrorResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.ArrayList;
import java.util.List;

public class DataException extends Exception implements ExceptionMapper<DataException> {

    @Override
    public Response toResponse(DataException exception) {
        List<ErrorResponse> errorResponses = new ArrayList<>();
        errorResponses.add(new ErrorResponse(Response.Status.NOT_FOUND.getStatusCode(), "Model not found"));
        return Response.status(Response.Status.NOT_FOUND).entity(errorResponses).build();
    }
}
