package in.gf2.mqtt.model;

import lombok.Data;

@Data
public class MqttSubscribeModel {

    private String message;
    private Integer qos;
    private Integer id;

}
