package io.eigr.functions.spawn.codebeambr.messaging;

import io.eigr.functions.spawn.codebeambr.messages.Request;

public interface Producer {
    void send(Request request);
}
