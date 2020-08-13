package com.example.encryption.key;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Version;
import org.springframework.beans.factory.annotation.Value;

@Entity
@Table(name = "keyservice_keys")
class KeyEntity {
  @Id
  @Column(name = "id")
  private UUID id;

  @Column(name="value")
  private String value;

  @Version
  @Column(name="version")
  private int version;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }
}
