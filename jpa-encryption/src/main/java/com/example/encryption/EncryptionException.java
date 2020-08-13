package com.example.encryption;

/**
 * Generic runtime exception used by this module to indicate errors.
 */
public class EncryptionException extends RuntimeException {

  public EncryptionException(String message) {
    super(message);
  }

  public EncryptionException(String message, Throwable cause) {
    super(message,cause);
  }

}
