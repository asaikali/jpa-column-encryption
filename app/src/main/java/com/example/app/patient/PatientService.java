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
    // gett all the contents of the database and return it as a list
    var result = new ArrayList<PatientJson>();
    this.patientRepository.findAll().forEach(
        patientEntity -> {
          var json = new PatientJson();
          json.setId(patientEntity.getId().toString());
          json.setName(patientEntity.getName());
          // get the social insurance notice that we don't need to do any decryption since
          // at this point the data has already been decryted from the database.
          json.setSin(patientEntity.getSin().getValue());
          result.add(json);
        }
    );

    return result;
  }

  @Transactional(propagation = Propagation.REQUIRED)
  public UUID create(String name, String sin) {

    // create the entity object to store in the database
    var patientEntity = new PatientEntity();
    patientEntity.setId(UUID.randomUUID());
    patientEntity.setName(name);

    // put the social insurance number in the sensitive value object and set the key
    // that should be used for encryption, the encryption of the sin will happen just
    // before the data is written to the database.
    var sinHolder  = new SensitiveStringValue();
    sinHolder.setKey(keyService.getKey(PatientEntity.class));
    sinHolder.setValue(sin);
    patientEntity.setSin(sinHolder);

    // save the object to the database and return the object id
    this.patientRepository.save(patientEntity);
    return patientEntity.getId();
  }
}
