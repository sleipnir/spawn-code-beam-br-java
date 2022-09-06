package io.eigr.functions.spawn.codebeambr.actors;

import io.eigr.functions.spawn.codebeambr.messages.Reply;
import io.eigr.functions.spawn.codebeambr.messages.Request;
import io.eigr.functions.spawn.codebeambr.state.actors.RobertState;
import io.eigr.spawn.springboot.starter.ActorContext;
import io.eigr.spawn.springboot.starter.Value;
import io.eigr.spawn.springboot.starter.annotations.ActorEntity;
import io.eigr.spawn.springboot.starter.annotations.Command;
import lombok.extern.log4j.Log4j2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Log4j2
@ActorEntity(name = "robert", stateType = RobertState.class, snapshotTimeout = 10000, deactivatedTimeout = 640000)
public final class RobertActor {
    @Command
    public Value setLanguage(Request request, ActorContext<RobertState> context) {
        log.info("RobertActor Received ´setLanguage´ invocation. Message: {}. Context: {}", request, context);
        List<String> stateLanguages = new ArrayList<>();

        if (context.getState().isPresent()) {
            log.info("State is present and value is {}", context.getState().get());
            Optional<RobertState> oldState = context.getState();
            stateLanguages.addAll(oldState.get().getLanguagesList());
            stateLanguages.add(request.getLanguage());

        } else {
            log.info("State is NOT present. RobertActor getLanguage is {}", request.getLanguage());
            stateLanguages = Arrays.asList(request.getLanguage());
        }

        String resp = String.format("Hello %s. My name is Robert and I am using Spawn Stateful Serverless platform powered by Erlang VM technology :-)", request.getLanguage());
        Reply reply = Reply.newBuilder().setResponse(resp).build();

        return Value.ActorValue.at()
                .value(reply)
                .state(RobertState.newBuilder().addAllLanguages(stateLanguages).build())
                .reply();
    }

    @Command
    public Value getActualState(ActorContext<RobertState> context) {
        log.info("RobertActor Received ´getActualState´ invocation. Context: {}", context);
        if (context.getState().isPresent()) {
            RobertState state = context.getState().get();

            return Value.ActorValue.<RobertState, RobertState>at()
                    .state(state)
                    .value(state)
                    .reply();
        }

        List<String> stateLanguages = new ArrayList<>();
        RobertState state = RobertState.newBuilder().addAllLanguages(stateLanguages).build();
        return Value.ActorValue.<RobertState, RobertState>at()
                .state(state)
                .value(state)
                .reply();
    }
}
