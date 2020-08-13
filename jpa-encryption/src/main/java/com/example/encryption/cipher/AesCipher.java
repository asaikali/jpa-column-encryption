package com.example.encryption.cipher;


import com.example.encryption.EncryptionException;
import com.example.encryption.cipher.SymmetricCipher;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public final class AesCipher implements SymmetricCipher {

  private final SecretKey key;
  private final String algorithm = "AES/CBC/PKCS5Padding";

  public AesCipher(String password, String salt) {
    try {
      var spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), 65536, 256);
      var factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
      SecretKey tmp = factory.generateSecret(spec);
      this.key = new SecretKeySpec(tmp.getEncoded(), "AES");
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new EncryptionException("Unable to create key",e);
    }
  }

  public AesCipher(SecretKey secretKey) {
    if (!secretKey.getAlgorithm().equals("AES_256")) {
      throw new EncryptionException("AES-256 Key required");
    }
    this.key = secretKey;
  }

  @Override
  public SecretKey getKey() {
    return key;
  }

  @Override
  public byte[] encrypt(byte[] clearText) {
    try {
      var cipher = createCipher();
      cipher.init(Cipher.ENCRYPT_MODE, key);
      return cipher.doFinal(clearText);
    } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
      throw new EncryptionException("Unable to encrypt data", e);
    }
  }

  @Override
  public byte[] decrypt(byte[] cipherText) {
    try {
      var cipher = createCipher();
      cipher.init(Cipher.DECRYPT_MODE, key);
      return cipher.doFinal(cipherText);
    } catch (InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
      throw new EncryptionException("Unable to encrypt data", e);
    }
  }

  private Cipher createCipher() {
    try {
      return Cipher.getInstance(algorithm);
    } catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
      throw new EncryptionException(String.format("Unable to obtain cipher '%s'", algorithm), e);
    }
  }
}
