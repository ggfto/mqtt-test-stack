package in.gf2.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import in.gf2.config.AppConfig;
import lombok.RequiredArgsConstructor;

@Component @RequiredArgsConstructor
public class MqttConfig {
	
	Logger log = LoggerFactory.getLogger(getClass());
		
	private	final AppConfig appConfig;
    private static IMqttClient instance;

    public IMqttClient getInstance() {
        try {
            if (instance == null) {
            	log.trace(appConfig.toString());
                instance = new MqttClient(appConfig.getEndpoint(), appConfig.getMqttClientid());
            }

            MqttConnectOptions options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);

            if (!instance.isConnected()) {
                instance.connect(options);
            }
        } catch (MqttException e) {
            e.printStackTrace();
        }

        return instance;
    }
}
