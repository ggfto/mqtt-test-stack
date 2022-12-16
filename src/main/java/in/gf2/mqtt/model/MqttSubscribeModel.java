package in.gf2.mqtt.model;

import com.fasterxml.jackson.databind.JsonNode;

import lombok.Data;

@Data
public class MqttSubscribeModel {

    private JsonNode message;
    private Integer qos;
    private Integer id;

}
