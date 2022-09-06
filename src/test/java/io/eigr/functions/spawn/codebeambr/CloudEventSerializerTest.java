package io.eigr.functions.spawn.codebeambr;

import io.cloudevents.CloudEvent;
import io.cloudevents.core.builder.CloudEventBuilder;
import io.cloudevents.core.provider.EventFormatProvider;
import io.cloudevents.jackson.JsonFormat;
import io.eigr.functions.spawn.codebeambr.messages.Request;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.UUID;

public class CloudEventSerializerTest {

    @Test
    public void shouldOutputToJson_Test() {
        Request request = Request.newBuilder()
                .setLanguage("Java")
                .build();

        CloudEvent event = CloudEventBuilder.v1()
                .withType("joe")
                .withData(request.toByteArray())
                .withId(UUID.randomUUID().toString())
                .withSource(URI.create("http://localhost"))
                .build();

        byte[] serialized = EventFormatProvider
                .getInstance()
                .resolveFormat(JsonFormat.CONTENT_TYPE)
                .serialize(event);

        System.out.println(String.format("CloudEvent from Request: %s", new String(serialized)));
    }
}
