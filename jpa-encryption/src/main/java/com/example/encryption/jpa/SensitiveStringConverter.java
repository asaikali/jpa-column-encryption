package com.example.encryption.jpa;

import com.example.encryption.SensitiveStringValue;
import com.example.encryption.key.KeyId;
import com.example.encryption.key.KeyService;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.stereotype.Component;

/**
 * The standard JPA attribute convertor which is used to encrypt/decrypt a database column as part
 * of persisting objects.
 * <p>
 * Spring Boot will take care of registering this convertor with Hibernate and since this object
 * is a bean  it can interact with key service to encrypt / decrypt the data.
 */
@Component
@Converter(autoApply = true)
public class SensitiveStringConverter implements AttributeConverter<SensitiveStringValue, String> {

  private final KeyService keyService;
  private final Encoder encoder = Base64.getUrlEncoder();
  private final Decoder decoder = Base64.getUrlDecoder();

  public SensitiveStringConverter(KeyService keyService) {
    this.keyService = keyService;
  }

  @Override
  public String convertToDatabaseColumn(SensitiveStringValue attribute) {
    // encrypt the data using the key set on the sensitive value before saving it to the database
    var encryptor = this.keyService.bytesEncryptor(attribute.getKey()).orElseThrow();
    StringBuilder sb = new StringBuilder(attribute.getKey().getId().toString());
    sb.append(":");
    sb.append(encoder.encodeToString(encryptor.encrypt(attribute.getValue().getBytes())));
    return sb.toString();
  }

  @Override
  public SensitiveStringValue convertToEntityAttribute(String dbData) {
    var values = dbData.split(":");
    var keyId = new KeyId(values[0]);
    var encryptor = this.keyService.bytesEncryptor(keyId).orElseThrow();
    var bytes = encryptor.decrypt(decoder.decode(values[1]));
    var result = new SensitiveStringValue();
    result.setValue(Utf8.decode(bytes));
    result.setKey(keyId);
    return result;
  }
}
