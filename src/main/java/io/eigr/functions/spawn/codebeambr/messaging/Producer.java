package io.eigr.functions.spawn.codebeambr.messaging;

import com.google.protobuf.Any;
import com.google.protobuf.InvalidProtocolBufferException;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;
import io.cloudevents.core.provider.EventFormatProvider;
import io.cloudevents.jackson.JsonFormat;

import io.eigr.functions.spawn.codebeambr.messages.Request;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.amqp.core.Queue;

import java.net.URI;
import java.util.Base64;
import java.util.UUID;

@Log4j2
@Component
public class Producer {

    @Autowired
    private Queue queue;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(Request request) throws InvalidProtocolBufferException {
        Any any = Any.pack(request);
        System.out.println(any);
        byte[] packPayload = any.toByteArray();
        String payloadBase64 = Base64.getEncoder().encodeToString(packPayload);

        byte[] decodedBase64 = Base64.getDecoder().decode(payloadBase64);
        Any unpackAny = Any.parseFrom(decodedBase64);
        Request unpackRequest = unpackAny.unpack(Request.class);
        log.info("Unpack request {}", unpackRequest);
        /*

        CloudEvent event = CloudEventBuilder.v1()
                .withType("spawn-data-types")
                .withData("text/plain", payloadBase64.getBytes())
                .withId(UUID.randomUUID().toString())
                .withSource(URI.create("http://localhost"))
                .build();

        byte[] payload = EventFormatProvider
                .getInstance()
                .resolveFormat(JsonFormat.CONTENT_TYPE)
                .serialize(event);

        String data = new String(payload);
        */

        log.info("Send message to queue {} with Payload: {}", this.queue.getName(), payloadBase64);
        rabbitTemplate.convertAndSend(this.queue.getName(), payloadBase64);
    }
}
