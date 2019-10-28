package coupons.exceptions;

import coupons.responses.ErrorResponse;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import java.util.ArrayList;
import java.util.List;

public class MyException extends Exception implements ExceptionMapper<MyException> {

    private String message;
    private Response.Status status;

    public MyException() {
    }

    public MyException(String message, Response.Status status) {
        this.message = message;
        this.status = status;
    }

    @Override
    public Response toResponse(MyException exception) {
        List<ErrorResponse> errors = new ArrayList<>();
        errors.add(new ErrorResponse(exception.getStatus().getStatusCode(), exception.getMessage()));
        return Response.status(exception.getStatus()).entity(errors).build();
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(Response.Status status) {
        this.status = status;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Response.Status getStatus() {
        return status;
    }
}
