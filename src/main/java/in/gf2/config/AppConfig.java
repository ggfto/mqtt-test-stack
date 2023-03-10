package in.gf2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data @Configuration
public class AppConfig {
	
	@Value("${mqtt.server:tcp://localhost}")
	private String mqttServer;
	
	@Value("${mqtt.port:1883}")
	private int mqttPort;
	
	@Value("${mqtt.clientid:spring-server}")
	private String mqttClientid;
	
	public String getEndpoint() {
		return this.mqttServer + ":" + this.mqttPort;
	}
}

