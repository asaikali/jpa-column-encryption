package com.example.encryption.key;

import java.util.UUID;
import org.springframework.data.repository.CrudRepository;

interface KeyEntityRepository extends CrudRepository<KeyEntity, UUID> {

}
