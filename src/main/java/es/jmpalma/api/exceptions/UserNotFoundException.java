package es.jmpalma.api.exceptions;

import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;

@RequiredArgsConstructor
public class UserNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 2432370183640012224L;
	private final Long id;

    @Override
    public String getMessage() {
        return MessageFormat.format("User with ID ''{0}'' isn''t available", id);
    }
}
