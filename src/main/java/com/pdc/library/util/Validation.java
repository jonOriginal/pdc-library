package com.pdc.library.util;

public class Validation {

    private Validation() {
    }

    public static void ensureInteger(String value) throws NumberFormatException {
        try {
            Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Value must be an integer: " + value, e);
        }
    }
}
