package in.gf2.mqtt.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MqttPublishModel {

    @NotNull
    @Size(min = 1,max = 255)
    private String topic;

    @NotNull
    private String message;

    @NotNull
    private Boolean retained;

    @NotNull
    private Integer qos;
}
