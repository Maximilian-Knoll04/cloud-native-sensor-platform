package de.maxknoll.app.repository;

import org.springframework.data.repository.CrudRepository;

public interface SensordataRepository extends CrudRepository<SensordataEntity,Long>
{
    public Iterable<SensordataEntity> findAllByUserId(Long userId);
}
