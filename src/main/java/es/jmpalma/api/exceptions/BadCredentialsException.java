package es.jmpalma.api.exceptions;

import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;

@RequiredArgsConstructor
public class BadCredentialsException extends RuntimeException {

	private static final long serialVersionUID = 6325370501822362925L;
	private final String email;

    @Override
    public String getMessage() {
        return MessageFormat.format("Email or password didn''t match for ''{0}''", email);
    }
}
