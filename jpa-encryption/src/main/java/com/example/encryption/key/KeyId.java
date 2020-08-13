package com.example.encryption.key;

import java.util.Objects;
import java.util.UUID;

/**
 * Immutable class that represents an encryption key ID.
 */
public final class KeyId {
  private final UUID id;

  public KeyId(UUID id) {
    this.id = id;
  }

  public KeyId(String id){
    this.id = UUID.fromString(id);
  }

  public UUID getId() {
    return id;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    KeyId keyId = (KeyId) o;
    return id.equals(keyId.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder("KeyId{");
    sb.append("id=").append(id);
    sb.append('}');
    return sb.toString();
  }
}
