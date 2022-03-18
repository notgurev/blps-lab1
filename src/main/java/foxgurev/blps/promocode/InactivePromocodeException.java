package foxgurev.blps.promocode;

import foxgurev.blps.exceptions.BadRequestException;

public class InactivePromocodeException extends BadRequestException {
    public InactivePromocodeException(String message) {
        super(message);
    }

    public InactivePromocodeException() {
    }
}
