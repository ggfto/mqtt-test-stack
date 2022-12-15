package in.gf2.mqtt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class MqttException extends RuntimeException{
	private static final long serialVersionUID = -2338530260360280001L;

	public MqttException(String message) {
        super(message);
    }
}