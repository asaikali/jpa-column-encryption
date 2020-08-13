package com.example.app.patient;

import com.example.encryption.SensitiveStringValue;
import com.example.encryption.key.KeyService;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PatientService {

  private final PatientRepository patientRepository;
  private final KeyService keyService;

  PatientService(PatientRepository patientRepository, KeyService keyService){
    this.patientRepository = patientRepository;
    this.keyService = keyService;
  }

  @Transactional(propagation = Propagation.SUPPORTS)
  List<PatientJson> getAll(){
    var result = new ArrayList<PatientJson>();
    this.patientRepository.findAll().forEach(
        patientEntity -> {
          var json = new PatientJson();
          json.setId(patientEntity.getId().toString());
          json.setName(patientEntity.getName());
          json.setSin(patientEntity.getSin().getValue());
          result.add(json);
        }
    );

    return result;
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public UUID create(String name, String sin) {
    var patientEntity = new PatientEntity();
    patientEntity.setId(UUID.randomUUID());
    patientEntity.setName(name);

    var sinHolder  = new SensitiveStringValue();
    sinHolder.setKey(keyService.getKey(PatientEntity.class));
    sinHolder.setValue(sin);
    patientEntity.setSin(sinHolder);

    this.patientRepository.save(patientEntity);
    return patientEntity.getId();
  }
}
