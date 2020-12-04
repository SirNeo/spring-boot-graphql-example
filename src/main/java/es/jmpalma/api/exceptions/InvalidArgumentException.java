package es.jmpalma.api.exceptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

public class InvalidArgumentException extends RuntimeException implements GraphQLError {

	private static final long serialVersionUID = 1L;
	
	private Map<String, Object> extensions = new HashMap<>();

    public InvalidArgumentException(String message, String invalidArgument) {
        super(message);
        extensions.put("invalidArgument", invalidArgument);
    }

    @Override
    public ErrorType getErrorType() {
        return ErrorType.ValidationError;
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
