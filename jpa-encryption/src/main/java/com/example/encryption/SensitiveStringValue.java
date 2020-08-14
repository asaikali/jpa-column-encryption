package com.example.encryption;


import java.util.Objects;

/**
 * A sensitive value that contains a String.
 */
public class SensitiveStringValue extends SensitiveValue<String> {

  private String value;

  @Override
  public SensitiveValue<String> setValue(String value) {
   this.value = value;
   return this;
  }

  @Override
  public String getValue() {
    return this.value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    SensitiveStringValue that = (SensitiveStringValue) o;
    return value.equals(that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
