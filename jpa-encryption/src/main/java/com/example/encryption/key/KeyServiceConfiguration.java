package com.example.encryption.key;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * The key service encrypts the keys it generates using a password and salt. This configuration
 * is a placeholder for the key service's key encryption key. You should get this value from a
 * key storage service in the real world production such as Vault or  a cloud KMS.
 */
@Configuration
@ConfigurationProperties(prefix = "keyservice")
class KeyServiceConfiguration {
  private String password;
  private String salt;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }
}
