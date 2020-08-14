package com.example.encryption.key;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("KeyId Test")
class KeyIdTest {

  @Test
  @DisplayName("test hashCode and equals")
  void testHashCodeEquals() {
    EqualsVerifier.forClass(KeyId.class).withNonnullFields("id").verify();
  }
}
