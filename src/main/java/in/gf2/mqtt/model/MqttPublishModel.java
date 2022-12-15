package in.gf2.mqtt.model;

import com.fasterxml.jackson.databind.JsonNode;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MqttPublishModel {

    @NotNull
    @Size(min = 1,max = 255)
    private String topic;

    @NotNull
    private JsonNode message;

    @NotNull
    private Boolean retained;

    @NotNull
    private Integer qos;
}
