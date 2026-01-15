package de.maxknoll.app.service;

import de.maxknoll.app.repository.SensordataEntity;
import de.maxknoll.app.repository.SensordataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SensordataService {

    private final SensordataRepository sensordataRepository;

    @Autowired
    public  SensordataService(SensordataRepository sensordataRepository) {
        this.sensordataRepository = sensordataRepository;
    }

    public SensordataEntity saveSensordata(UUID userId, Double temperature) {

        final SensordataEntity sensordataEntity = new SensordataEntity(userId, Instant.now() ,temperature);

        return sensordataRepository.save(sensordataEntity);
    }

    public Optional<List<SensordataEntity>> findAllByUserId(UUID userId) {
        final var data = sensordataRepository.findAllByUserId(userId);

        List<SensordataEntity> listOfData = Streamable.of(data).toList();
        if (listOfData.isEmpty()) {
            return Optional.empty();
        } else  {
            return Optional.of(listOfData);
        }
    }
}
