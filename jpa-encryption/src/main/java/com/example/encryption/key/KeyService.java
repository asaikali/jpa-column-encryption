package com.example.encryption.key;

import java.security.Key;
import java.util.Optional;
import org.springframework.security.crypto.encrypt.BytesEncryptor;

/**
 * The key service stores keys securely in the database. All keys are encrypted before they
 * are stored in the database.
 */
public interface KeyService {

  /**
   * Create a byte encryptor based on the spring security crypto module. This will configure a
   * standard password-based bytes encryptor using 256 bit AES encryption with
   * Galois Counter Mode (GCM). Derives the secret key using PKCS #5's PBKDF2
   * (Password-Based Key Derivation Function #2).
   *
   * @param keyId The key id to load from the database
   * @return an optional containing the bytes Encryptor.
   */
  Optional<BytesEncryptor> bytesEncryptor(KeyId keyId);


  /**
   * Returns a bytes encryptor based on the current name.
   *
   * @param name the name of the key to use to generate the bytes encryptor
   * @param generateIfMissing generate a key the name if it does not exist
   * @return
   */
  Optional<BytesEncryptor> bytesEncryptor(String name, boolean generateIfMissing);

  KeyId getKey(Class<?> clazz);

  /**
   * Generate a new key, store it in the database securely, give it a name and make it
   * the current key for thee name.
   *
   * @param name
   * @param current
   * @return
   */
  void generate(KeyId keyId, String name, boolean current);
}
