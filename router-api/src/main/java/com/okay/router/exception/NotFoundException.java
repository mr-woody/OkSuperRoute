
package com.okay.router.exception;

/**
 * The custom exception represents not found
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String detailMessage) {
        super(detailMessage);
    }
}
