package de.maxknoll.app.controller;

import de.maxknoll.app.controller.dto.SensordataDTOIn;
import de.maxknoll.app.controller.dto.SensordataDTOOut;
import de.maxknoll.app.repository.SensordataEntity;
import de.maxknoll.app.service.SensordataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/sensor-data")
public class SensordataController {

    private final SensordataService sensordataService;

    @Autowired
    public SensordataController(SensordataService sensordataService) {
        this.sensordataService = sensordataService;
    }

    @GetMapping
    public ResponseEntity<List<SensordataDTOOut>> getSensorData(@AuthenticationPrincipal OidcUser oidcUser) {
        UUID subjectId = UUID.fromString(oidcUser.getSubject());
        Optional<List<SensordataEntity>> entities = sensordataService.findAllByUserId(subjectId);
        if (entities.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<SensordataDTOOut> dtoOuts = entities.get().stream().map(SensordataDTOOut::new).toList();
            return ResponseEntity.ok(dtoOuts);
        }
    }

    @PostMapping
    public ResponseEntity<SensordataDTOOut> createSensorData(@RequestBody SensordataDTOIn sensordataDTOIn, @AuthenticationPrincipal OidcUser oidcUser) {
        UUID subjectId = UUID.fromString(oidcUser.getSubject());
        SensordataEntity createdEntity = sensordataService.saveSensordata(subjectId, sensordataDTOIn.temperature());
        return ResponseEntity.ok(new SensordataDTOOut(createdEntity));

    }
}
