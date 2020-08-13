package com.example.app.patient;


import com.example.encryption.SensitiveStringValue;
import com.example.encryption.jpa.SensitiveStringConverter;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="patients")
public class PatientEntity {

  @Id
  @Column(name="id")
  private UUID id;

  @Column(name="name")
  private String name;

  @Column(name="sin")
  @Convert(converter = SensitiveStringConverter.class)
  private SensitiveStringValue sin;

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public SensitiveStringValue getSin() {
    return sin;
  }

  public void setSin(SensitiveStringValue sin) {
    this.sin = sin;
  }
}
