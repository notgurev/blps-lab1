package foxgurev.blps.auth.theirs;

public class UnauthorizedException extends Exception {
    public UnauthorizedException(final String message) {
        super(message);
    }
}
