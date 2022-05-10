package guru.springframework.sfgjms.listener;

import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.model.HelloWorldMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.Message;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class HelloListener {

  private final JmsTemplate jmsTemplate;

  @JmsListener(destination = JmsConfig.MY_QUEUE)
  public void listen(@Payload HelloWorldMessage helloWorldMessage,
                     @Headers MessageHeaders headers, Message message) {

//    log.info("I'm Got a message!");
//
//    log.info("Message: {}", helloWorldMessage);
  }

  @JmsListener(destination = JmsConfig.MY_SND_RCV_QUEUE)
  public void listenHello(@Payload HelloWorldMessage helloWorldMessage,
                     @Headers MessageHeaders headers, Message message) throws JMSException {

    HelloWorldMessage helloWorld = HelloWorldMessage.builder()
        .id(UUID.randomUUID())
        .message("World!")
        .build();

    jmsTemplate.convertAndSend(message.getJMSReplyTo(), helloWorld);

    log.info("I'm Got a message!");

    log.info("Message: {}", helloWorldMessage);
  }
}
