package io.eigr.functions.spawn.codebeambr.messaging;

import io.eigr.functions.spawn.codebeambr.messages.Request;
import io.eigr.functions.spawn.codebeambr.messaging.encoders.EventEncoder;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class RabbitMQProducer implements Producer {

    @Autowired
    private Queue queue;

    @Autowired
    private EventEncoder encoder;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void send(Request request) {
        Object packPayload = encoder.encode(request);

        log.info("Send message to queue {} with Payload: {}", this.queue.getName(), packPayload);
        rabbitTemplate.convertAndSend(this.queue.getName(), packPayload);
    }
}
