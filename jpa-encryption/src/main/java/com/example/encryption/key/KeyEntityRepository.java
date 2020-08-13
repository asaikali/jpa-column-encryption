package com.example.encryption.key;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

interface KeyEntityRepository extends CrudRepository<KeyEntity, UUID> {

  @Query("SELECT k FROM KeyEntity as k where k.current=true AND k.name=:name")
  Optional<KeyEntity> findCurrentKeyByName(String name);
}
