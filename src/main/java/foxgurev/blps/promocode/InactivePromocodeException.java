package foxgurev.blps.promocode;

import foxgurev.blps.exceptions.VisibleException;

public class InactivePromocodeException extends VisibleException {
    public InactivePromocodeException(String message) {
        super(message);
    }

    public InactivePromocodeException() {
    }
}
