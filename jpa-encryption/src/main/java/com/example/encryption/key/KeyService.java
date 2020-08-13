package com.example.encryption.key;

import java.security.Key;
import java.util.Optional;

/**
 * The key service stores keys securely in the database. All keys are encrypted before they
 * are stored in the database.
 */
public interface KeyService {

  /**
   * Loads the key from the database. In the database keys are encrypted so this method will
   * decrypt the encrypted key using the KeyService configuration.
   *
   * @param keyId The key id to load from the database
   * @return an optional containing the key.
   */
  Optional<Key> loadKey(KeyId keyId);

  /**
   * Stores the specified key under the specified keyid. The passed in key will be encrypted before
   * it is stored in the database.
   *
   * @param keyId
   * @param key
   */
  void saveKey(KeyId keyId, Key key);
}
