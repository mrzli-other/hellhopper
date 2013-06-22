package com.turbogerm.hellhopper.util;

public final class ExceptionThrower {
    
    public static void throwException(String format, Object... args) {
        String errorMessage = String.format(format, args);
        throwException(errorMessage);
    }
    
    public static void throwException(String errorMessage) {
        Logger.error(errorMessage);
        throw new RuntimeException(errorMessage);
    }
}