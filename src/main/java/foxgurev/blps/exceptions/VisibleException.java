package foxgurev.blps.exceptions;

/**
 * VisibleException, в отличие от остальных, виден пользователю при ошибочном запросе.
 * При других исключениях возвращается "Internal server error"
 */
public class VisibleException extends RuntimeException {
    public VisibleException(String message) {
        super(message);
    }

    public VisibleException() {
    }
}
