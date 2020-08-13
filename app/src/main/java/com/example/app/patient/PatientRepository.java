package com.example.app.patient;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

interface PatientRepository extends CrudRepository<PatientEntity, UUID> {

}
