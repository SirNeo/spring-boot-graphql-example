package es.jmpalma.api.exceptions;

import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;

@RequiredArgsConstructor
public class UserAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = -784747073668725671L;
	private final String email;

    @Override
    public String getMessage() {
        return MessageFormat.format("A user already exists with email ''{0}''", email);
    }
}
