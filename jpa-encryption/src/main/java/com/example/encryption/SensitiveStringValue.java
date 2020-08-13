package com.example.encryption;


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
}
