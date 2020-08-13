package com.example.encryption;

import com.example.encryption.key.KeyId;
import java.util.UUID;

/**
 * A holder of sensitive data. An instance of this interface holds sensitive data
 * in clear text in the JVM's Memory. By the storing the clear text data in an instance of this
 * interface other frameworks such as JPA can automatically encrypt the data before it
 * stored in the database and decrypt it when it read from the database. The interface
 * is also a reminder to developer not to write the contents of the sensitive value
 * to a log file or transfer it over the network in clear text.
 *
 * @param <T> The type of object
 */
public abstract class SensitiveValue<T> {

  private KeyId keyId;

  /**
   * Set the key id that should be used to encrypt this value
   * @param keyId
   */
  public SensitiveValue<T> setKey(KeyId keyId) {
    this.keyId = keyId;
    return this;
  }

  /**
   * Return the keyId to use to encrypt this value.
   */
  public KeyId getKey() {
    return this.keyId;
  }

  /**
   * set the value that should be encrypted / decrypted with the key
   */
  public abstract SensitiveValue<T> setValue(T value);

  /**
   * return the value stored in this object.
   */
  public abstract T getValue();

}
