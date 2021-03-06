package com.github.boukefalos.jlibloader;

/**
 * Thrown when a given integration is not available for the current machine.
 */
public class NativeLibraryUnavailableException extends NativeException {
    private static final long serialVersionUID = 1L;

    public NativeLibraryUnavailableException(String message) {
        super(message);
    }
}
