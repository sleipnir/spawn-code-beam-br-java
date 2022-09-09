package io.eigr.functions.spawn.codebeambr.messaging.encoders;

import com.google.protobuf.Any;
import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;
import io.cloudevents.core.provider.EventFormatProvider;
import io.cloudevents.protobuf.ProtobufFormat;
import io.eigr.functions.spawn.codebeambr.messages.Request;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.util.UUID;

@Component
@ConditionalOnProperty(
        value="io.eigr.functions.spawn.codebeambr.encoder",
        havingValue = "cloudevent-proto",
        matchIfMissing = false)
public class CloudEventEncoder implements EventEncoder{

    @Override
    public byte[] encode(Request request) {
        Any any = Any.pack(request);
        byte[] packPayload = any.toByteArray();

        CloudEvent event = CloudEventBuilder.v1()
                .withId(UUID.randomUUID().toString())
                .withType("spawn-type")
                .withData(packPayload)
                .withSource(URI.create("http://localhost"))
                .build();

        return EventFormatProvider
                .getInstance()
                .resolveFormat(ProtobufFormat.PROTO_CONTENT_TYPE)
                .serialize(event);
    }
}
