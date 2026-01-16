package de.maxknoll.app.controller;

import de.maxknoll.app.controller.dto.SensordataDTOIn;
import de.maxknoll.app.controller.dto.SensordataDTOOut;
import de.maxknoll.app.repository.SensordataEntity;
import de.maxknoll.app.service.SensordataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/sensor-data")
public class SensordataController {

    Logger logger = LoggerFactory.getLogger(SensordataController.class);

    private final SensordataService sensordataService;

    @Autowired
    public SensordataController(SensordataService sensordataService) {
        this.sensordataService = sensordataService;
    }

    @GetMapping
    public String handleGetSensorData(@AuthenticationPrincipal OidcUser oidcUser, Model model) {
        model.addAttribute("newSensorDate", new SensordataDTOIn(null));
        UUID subjectId = UUID.fromString(oidcUser.getSubject());
        Optional<List<SensordataEntity>> entities = sensordataService.findAllByUserId(subjectId);
        if (entities.isEmpty()) {
            return "dashboard";
        } else {
            List<SensordataDTOOut> dtoOuts = entities.get().stream().map(SensordataDTOOut::new).toList();
            model.addAttribute("sensorData", dtoOuts);
            return "dashboard";
        }
    }

    @PostMapping
    public String handleCreateSensorData(@ModelAttribute("newSensorDate") SensordataDTOIn sensordataDTOIn, @AuthenticationPrincipal OidcUser oidcUser, Model model) {

        UUID subjectId = UUID.fromString(oidcUser.getSubject());
        SensordataEntity createdEntity = sensordataService.saveSensordata(subjectId, sensordataDTOIn.temperature());

        logger.info("User " + subjectId + " created a new SensorDate with id " + createdEntity.getId());

        return "redirect:/sensor-data";

    }
}
