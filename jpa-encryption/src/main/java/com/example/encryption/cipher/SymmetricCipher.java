package com.example.encryption.cipher;

import javax.crypto.SecretKey;

/**
 * An encryptor / decryptor using a single key that is part of the
 * implementation.
 *
 */
public interface SymmetricCipher {

  /**
   * Returns the key that this application cipher will use to encrypt and decrypt data.
   */
  SecretKey getKey();

  /**
   * Encrypts the data using the key returned by getKey()
   *
   * @param clearText the bytes to encrypt
   * @return the encrypted bytes
   */
  byte[] encrypt(byte[] clearText);

  /**
   * decrypt the data using the key returned by getKey()
   *
   * @param cipherText the bytes to decrypt
   * @return the encrypted bytes
   */
  byte[] decrypt(byte[] cipherText);
}
