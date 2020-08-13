package com.example.encryption.key;

import java.security.Key;
import java.util.Optional;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
class KeyServiceImpl implements KeyService {
  private final KeyEntityRepository keyEntityRepository;
  private final KeyServiceConfiguration keyServiceConfiguration;

  KeyServiceImpl(KeyEntityRepository keyEntityRepository, KeyServiceConfiguration keyServiceConfiguration) {
    this.keyEntityRepository = keyEntityRepository;
    this.keyServiceConfiguration = keyServiceConfiguration;
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS)
  public Optional<Key> loadKey(KeyId keyId) {
    this.keyEntityRepository.findById(keyId.getId()).map(keyEntity -> {
      var key =  newEncryptor().decrypt(keyEntity.getValue()).split(":");
      return Encryptors.stronger(key[0],key[1]);
    });
    return Optional.empty();
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void saveKey(KeyId keyId, Key key) {
  }

  private TextEncryptor newEncryptor() {
    return Encryptors
        .delux(keyServiceConfiguration.getPassword(), keyServiceConfiguration.getSalt());
  }
}
