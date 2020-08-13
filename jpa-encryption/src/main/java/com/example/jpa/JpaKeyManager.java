package com.example.jpa;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
class JpaKeyManager {
  private final String keyEncryptionKey;

  public JpaKeyManager(@Value("${jpa.kek}") String key) {
    this.keyEncryptionKey = key;
  }


}
