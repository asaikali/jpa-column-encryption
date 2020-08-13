package com.example.app.patient;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class PatientController {

  private final PatientService patientService;

  public PatientController(PatientService patientService) {
    this.patientService = patientService;
  }

  @GetMapping("/")
  List<PatientJson> getAll() {
   return this.patientService.getAll();
  }

  @PostMapping("/patients")
  PatientJson create(@RequestBody PatientJson patientJson) {
    var id = this.patientService.create(patientJson.getName(),patientJson.getSin());
    patientJson.setId(id.toString());
    return patientJson;
  }
}
