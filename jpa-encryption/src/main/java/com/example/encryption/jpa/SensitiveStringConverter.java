package com.example.encryption.jpa;

import com.example.encryption.SensitiveStringValue;
import com.example.encryption.SensitiveValue;
import com.example.encryption.key.KeyId;
import com.example.encryption.key.KeyService;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import javax.persistence.AttributeConverter;
import org.springframework.security.crypto.codec.Utf8;
import org.springframework.stereotype.Component;

@Component
public class SensitiveStringConverter implements AttributeConverter <SensitiveValue<String>,String> {

  private final KeyService keyService;
  private final Encoder encoder = Base64.getUrlEncoder();
  private final Decoder decoder = Base64.getUrlDecoder();

  public SensitiveStringConverter(KeyService keyService) {
    this.keyService = keyService;
  }

  @Override
  public String convertToDatabaseColumn(SensitiveValue<String> attribute) {
    var encryptor = this.keyService.bytesEncryptor(attribute.getKey()).orElseThrow();
    StringBuilder sb = new StringBuilder(attribute.getKey().getId().toString());
    sb.append(":");
    sb.append(encoder.encodeToString(encryptor.encrypt(attribute.getValue().getBytes())));
    return sb.toString();
  }

  @Override
  public SensitiveValue<String> convertToEntityAttribute(String dbData) {
    var values = dbData.split(":");
    var keyId = new KeyId(values[0]);
    var encryptor = this.keyService.bytesEncryptor(keyId).orElseThrow();
    var bytes = encryptor.decrypt(decoder.decode(values[1]));
    var result = new SensitiveStringValue();
    result.setValue( Utf8.decode(bytes));
    result.setKey(keyId);
    return result;
  }
}
