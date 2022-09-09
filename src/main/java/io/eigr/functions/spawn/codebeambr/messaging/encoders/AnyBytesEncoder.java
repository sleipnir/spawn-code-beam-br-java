package io.eigr.functions.spawn.codebeambr.messaging.encoders;

import com.google.protobuf.Any;
import io.eigr.functions.spawn.codebeambr.messages.Request;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
@ConditionalOnProperty(
        value="io.eigr.functions.spawn.codebeambr.encoder",
        havingValue = "raw",
        matchIfMissing = false)
public class AnyBytesEncoder implements EventEncoder{

    @Override
    public Object encode(Request request) {
        Any any = Any.pack(request);
        byte[] packPayload = any.toByteArray();

        return Base64.getEncoder().encodeToString(packPayload);
    }
}
