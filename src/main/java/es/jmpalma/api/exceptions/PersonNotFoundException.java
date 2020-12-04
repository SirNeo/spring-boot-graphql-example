package es.jmpalma.api.exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class PersonNotFoundException extends RuntimeException implements GraphQLError {

	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> extensions = new HashMap<>();

    public PersonNotFoundException(String message, String invalidPersonId) {
        super(message);
        extensions.put("invalidPersonId", invalidPersonId);
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.DataFetchingException;
    }

    @Override
    public Map<String, Object> getExtensions() {
        return extensions;
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }
}
