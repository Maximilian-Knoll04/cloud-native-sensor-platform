package de.maxknoll.app.controller.dto;

import de.maxknoll.app.repository.SensordataEntity;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Locale;

public record SensordataDTOOut(String timestamp, Double temperature) {

    private static final DateTimeFormatter instantFormatter =
            DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).withLocale(Locale.GERMAN).withZone(ZoneId.systemDefault());

    public SensordataDTOOut(SensordataEntity sensordataEntity) {
        this(instantFormatter.format(sensordataEntity.getTimestamp()), sensordataEntity.getTemperature());
    }
}
