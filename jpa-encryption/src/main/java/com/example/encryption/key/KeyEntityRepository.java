package com.example.encryption.key;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

interface KeyEntityRepository extends CrudRepository<KeyEntity, UUID> {

  /**
   * Find the current key with the specified name. There can be multiple keys
   * with the same name but there should only be one key that is considered to
   * be the current key.
   *
   * @param name the key name
   * @return the current key matching the name if  it exists
   */
  @Query("SELECT k FROM KeyEntity as k where k.current=true AND k.name=:name")
  Optional<KeyEntity> findCurrentKeyByName(String name);
}
