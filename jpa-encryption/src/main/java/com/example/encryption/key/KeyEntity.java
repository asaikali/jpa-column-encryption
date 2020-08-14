package com.example.encryption.key;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Database representation of a key used to encrypt column data. Multiple keys can have the same
 * name but only one of them  should be marked as the  current key.
 */
@Entity
@Table(name = "keyservice_keys")
class KeyEntity {
  @Id
  @Column(name = "id")
  private UUID id;

  @Column(name="value")
  private String value;

  @Column(name="name")
  private String name;

  @Column(name = "current")
  private Boolean current;

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

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getCurrent() {
    return current;
  }

  public void setCurrent(Boolean current) {
    this.current = current;
  }
}
