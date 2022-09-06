package io.eigr.functions.spawn.codebeambr.messaging;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import io.eigr.functions.spawn.codebeambr.messages.Request;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Log4j2
@Component
public class Producer {

    @Autowired
    private Queue queue;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(Request request) throws InvalidProtocolBufferException {
        Any any = Any.pack(request);
        byte[] packPayload = any.toByteArray();
        String payloadBase64 = Base64.getEncoder().encodeToString(packPayload);

        log.info("Send message to queue {} with Payload: {}", this.queue.getName(), payloadBase64);
        rabbitTemplate.convertAndSend(this.queue.getName(), payloadBase64);
    }
}
