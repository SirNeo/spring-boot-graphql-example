package es.jmpalma.api.exceptions;

public class BadTokenException extends RuntimeException {
	private static final long serialVersionUID = -1284545344201442330L;

	@Override
    public String getMessage() {
        return "Token is invalid or expired";
    }
}
