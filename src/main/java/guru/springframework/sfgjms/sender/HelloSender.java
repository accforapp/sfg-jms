package guru.springframework.sfgjms.sender;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class HelloSender {

  private final JmsTemplate jmsTemplate;
  private final ObjectMapper objectMapper;

  @Scheduled(fixedRate = 2000)
  public void sendMessage() {

    HelloWorldMessage helloWorld = HelloWorldMessage.builder()
        .id(UUID.randomUUID())
        .message("Hello World")
        .build();

    jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, helloWorld);
  }

  @Scheduled(fixedRate = 2000)
  public void sendAndReceiveMessage() throws JMSException {

    HelloWorldMessage helloWorld = HelloWorldMessage.builder()
        .id(UUID.randomUUID())
        .message("Hello")
        .build();

    Message message = jmsTemplate.sendAndReceive(JmsConfig.MY_SND_RCV_QUEUE, session -> createTextMessage(session, helloWorld));

    if (message != null) {
      log.info("Received message: {}", message.getBody(String.class));
    }
  }

  private Message createTextMessage(Session session, HelloWorldMessage message) {

    Message helloMessage = null;

    try {
      helloMessage = session.createTextMessage(objectMapper.writeValueAsString(message));
      helloMessage.setStringProperty("_type", "guru.springframework.sfgjms.model.HelloWorldMessage");

      log.info("Sending Hello");
    } catch (Exception e) {
      log.error("Captured exception: {} - {}", e.getClass().getSimpleName(), e.getMessage(), e);
    }

    return helloMessage;
  }
}
