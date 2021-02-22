package com.petriuk.rest;

import org.json.JSONObject;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ValidationExceptionMapper
    implements ExceptionMapper<ConstraintViolationException> {

    @Override
    public Response toResponse(final ConstraintViolationException exception) {
        return Response.status(422)
            .entity(prepareResponse(exception).toString())
            .type("application/json").build();
    }

    private JSONObject prepareResponse(ConstraintViolationException exception) {
        JSONObject jsonObject = new JSONObject();

        for (ConstraintViolation<?> cv : exception.getConstraintViolations()) {
            jsonObject.put(getFieldName(cv.getPropertyPath()), cv.getMessage());
        }
        return jsonObject;
    }

    private String getFieldName(Path path) {
        String[] nodesString = path.toString().split("\\.");
        String fieldName = nodesString[nodesString.length - 1];
        return fieldName.equals("arg0") ? "passwordMatches" : fieldName;
    }
}