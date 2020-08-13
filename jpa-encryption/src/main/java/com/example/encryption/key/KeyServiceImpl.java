package com.example.encryption.key;

import java.util.Optional;
import java.util.UUID;
import javax.swing.text.html.Option;
import org.springframework.security.crypto.codec.Hex;
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

  KeyServiceImpl(KeyEntityRepository keyEntityRepository,
      KeyServiceConfiguration keyServiceConfiguration) {
    this.keyEntityRepository = keyEntityRepository;
    this.keyServiceConfiguration = keyServiceConfiguration;
  }

  @Override
  @Transactional(propagation = Propagation.SUPPORTS)
  public Optional<BytesEncryptor> bytesEncryptor(KeyId keyId) {
    return this.keyEntityRepository.findById(keyId.getId()).map(keyEntity -> {
      var value = newEncryptor().decrypt(keyEntity.getValue());
      var parts = value.split(":");
      return Encryptors.stronger(parts[1], parts[0]);
    });
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public Optional<BytesEncryptor> bytesEncryptor(String name, boolean generateIfMissing) {
    var keyOptional = this.keyEntityRepository.findCurrentKeyByName(name);
    KeyEntity keyEntity;
    if (keyOptional.isEmpty()) {
      if (generateIfMissing) {
        keyEntity = this.generateInternal(new KeyId(UUID.randomUUID()), name, true);
      } else {
        return Optional.empty();
      }
    } else {
      keyEntity = keyOptional.get();
    }

    var key = newEncryptor().decrypt(keyEntity.getValue()).split(":");
    var encryptor = Encryptors.stronger(key[1], key[0]);
    return Optional.of(encryptor);
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public KeyId getKey(Class<?> clazz) {
    var name = clazz.getName();
    var optional = this.keyEntityRepository.findCurrentKeyByName(name);
    if(optional.isEmpty()) {
      var  keyId = new KeyId(UUID.randomUUID());
      this.generateInternal(keyId, name, true);
      return keyId;
    } else {
      return new KeyId(optional.get().getId());
    }
  }

  @Override
  @Transactional(propagation = Propagation.REQUIRED)
  public void generate(KeyId keyId, String name, boolean current) {
    this.generateInternal(keyId, name, current);
  }


  public KeyEntity generateInternal(KeyId keyId, String name, boolean current) {
    // create key entity
    var keyEntity = new KeyEntity();
    keyEntity.setId(keyId.getId());
    keyEntity.setCurrent(current);
    keyEntity.setName(name);

    // compute the encrypted key value
    var password = UUID.randomUUID().toString();
    var salt = Hex.encode(UUID.randomUUID().toString().getBytes());
    StringBuilder sb = new StringBuilder();
    sb.append(salt);
    sb.append(":");
    sb.append(password);
    var value = newEncryptor().encrypt(sb.toString());
    keyEntity.setValue(value);

    // save the key to the database
    this.keyEntityRepository.save(keyEntity);

    return keyEntity;
  }

  private TextEncryptor newEncryptor() {
    return Encryptors
        .delux(keyServiceConfiguration.getPassword(), keyServiceConfiguration.getSalt());
  }
}
