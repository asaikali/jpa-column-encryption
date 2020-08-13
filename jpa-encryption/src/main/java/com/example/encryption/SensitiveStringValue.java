package com.example.encryption;


public class SensitiveStringValue implements SensitiveValue<String> {

  private String value;

  @Override
  public void set(String value) {
   this.value = value;
  }

  @Override
  public String get() {
    return this.value;
  }
}
