package io.eigr.functions.spawn.codebeambr.messaging.encoders;

import io.eigr.functions.spawn.codebeambr.messages.Request;

public interface EventEncoder {
    Object encode(Request request) ;
}
