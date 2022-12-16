package in.gf2.mqtt;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import in.gf2.mqtt.exception.ExceptionMessages;
import in.gf2.mqtt.exception.MqttException;
import in.gf2.mqtt.model.MqttPublishModel;
import in.gf2.mqtt.model.MqttSubscribeModel;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController @RequiredArgsConstructor
public class MqttController {

	Logger log = LoggerFactory.getLogger(getClass());
	
	private static final int WAIT_TIME = 100;
	
	@Autowired
	private MqttConfig config;
	
	private List<MqttSubscribeModel> messages;
	
    @PostMapping("publish")
    public ResponseEntity<?> publishMessage(@RequestBody @Valid MqttPublishModel messagePublishModel,
                               BindingResult bindingResult) throws Exception {
    	try {
	        if (bindingResult.hasErrors()) {
	            throw new MqttException(ExceptionMessages.SOME_PARAMETERS_INVALID);
	        }
	
	        MqttMessage mqttMessage = new MqttMessage(messagePublishModel.getMessage().toString().getBytes());
	        mqttMessage.setQos(messagePublishModel.getQos());
	        mqttMessage.setRetained(messagePublishModel.getRetained());
	        log.trace(messagePublishModel.toString());
	        config.getInstance().publish(messagePublishModel.getTopic(), mqttMessage);
	        return ResponseEntity.ok("Sucesso!");
    	} catch(Exception e) {
    		log.error("Error in publishing", e);
    		return ResponseEntity.badRequest().body(e.getMessage());
    	}
    }

    @GetMapping("subscribe")
    public ResponseEntity<List<MqttSubscribeModel>> subscribeChannel(@RequestParam(value = "topic") String topic,
                                                     @RequestParam(value = "wait_millis", required = false) Integer waitMillis) throws InterruptedException {
    	try {
	    	if(messages == null) messages = new ArrayList<>();
	        
	    	CountDownLatch countDownLatch = new CountDownLatch(10);
	        config.getInstance().subscribeWithResponse(topic, (s, mqttMessage) -> {
	            MqttSubscribeModel mqttSubscribeModel = new MqttSubscribeModel();
	            mqttSubscribeModel.setId(mqttMessage.getId());
	            mqttSubscribeModel.setMessage(new ObjectMapper().readTree(mqttMessage.getPayload()));
	            mqttSubscribeModel.setQos(mqttMessage.getQos());
	            if(!messages.contains(mqttSubscribeModel)) messages.add(mqttSubscribeModel);
	            log.trace(mqttSubscribeModel.toString());
	            countDownLatch.countDown();
	        });
	        
	        countDownLatch.await((waitMillis != null ? waitMillis : WAIT_TIME), TimeUnit.MILLISECONDS);
	        return ResponseEntity.ok(messages);
    	} catch(InterruptedException e) {
    		throw e;
    	} catch(Exception e) {
    		log.error(String.format("Error reading topic: [%s]", topic), e);
    		return ResponseEntity.badRequest().build();
    	}
    }
    
    @GetMapping("messages")
    public ResponseEntity<List<MqttSubscribeModel>> getMessages(@RequestParam(value = "topic") String topic) {
    	List<MqttSubscribeModel> result = new ArrayList<>();
    	if(messages != null) {
    		result.addAll(messages);
    		messages.clear();
    	}
    	
    	return ResponseEntity.ok(result);
    }
}
