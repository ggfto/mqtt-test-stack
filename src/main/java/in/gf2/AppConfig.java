package in.gf2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Configuration
@Getter @Setter @RequiredArgsConstructor
public class AppConfig {
	
	@Value("${mqtt.server:tcp://localhost}")
	private String mqttServer;
	
	@Value("${mqtt.port:1883}")
	private int mqttPort;
	
	@Value("${mqtt.clientid:spring-server}")
	private String mqttClientid;
}

