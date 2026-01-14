package de.maxknoll.app.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface SensordataRepository extends CrudRepository<SensordataEntity,UUID>
{
    public Iterable<SensordataEntity> findAllByUserId(UUID userId);
}
