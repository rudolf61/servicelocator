package nl.degrijs.servicelocator;

public class ValidateServiceParameters {
    public static void notNull(Object obj, String message) {
        if (obj == null) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void notEmpty(String val, String message) {
        if (val == null || val.isEmpty()) {
            throw new IllegalArgumentException(message);
        }
    }
}
