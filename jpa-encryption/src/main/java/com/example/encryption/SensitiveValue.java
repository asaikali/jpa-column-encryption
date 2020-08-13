package com.example.encryption;

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
public interface SensitiveValue<T> {

  /**
   *
   */
  void set(T value);

  T get();

  default void erase(){
    set(null);
  }
}
