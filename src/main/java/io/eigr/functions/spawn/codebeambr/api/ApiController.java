package io.eigr.functions.spawn.codebeambr.api;

import io.eigr.functions.spawn.codebeambr.messages.Reply;
import io.eigr.functions.spawn.codebeambr.messages.Request;
import io.eigr.functions.spawn.codebeambr.messaging.RabbitMQProducer;
import io.eigr.functions.spawn.codebeambr.state.actors.JoeState;
import io.eigr.functions.spawn.codebeambr.state.actors.RobertState;
import io.eigr.spawn.springboot.starter.SpawnSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@RestController
@RequestMapping("/api/v1")
public final class ApiController {

    @Autowired
    private SpawnSystem actorSystem;

    @Autowired
    private RabbitMQProducer rabbitMQProducer;

    @GetMapping("/actors/{name}")
    public Mono<ResponseEntity<String>> getActorState(@PathVariable String name) throws Exception {
        List<String> languages = new ArrayList<>();

        switch (name.toLowerCase(Locale.ROOT)) {
            case "joe":
                JoeState joeState = (JoeState) actorSystem.invoke(name, "getActualState", JoeState.class);
                if (joeState.getLanguagesList().isEmpty()) {
                    languages.addAll(new ArrayList<>());
                }
                languages.addAll(joeState.getLanguagesList());

                break;
            case "robert":
                RobertState robertState = (RobertState) actorSystem.invoke(name, "getActualState", RobertState.class);
                if (robertState.getLanguagesList().isEmpty()) {
                    languages.addAll(new ArrayList<>());
                }
                languages.addAll(robertState.getLanguagesList());

               break;
            default:
                languages.add("unknown-language");
        }

        return Mono.just(ResponseEntity
                .ok()
                .contentType(MediaType.TEXT_PLAIN)
                .body(String.join(",", languages)));
    }

    @PostMapping("/actors/{name}/{language}")
    public Mono<ResponseEntity<String>> setLanguage(@PathVariable String name, @PathVariable String language) throws Exception {
        Request request = Request.newBuilder().setLanguage(language).build();
        switch (name.toLowerCase(Locale.ROOT)) {
            case "joe":
                Reply reply = (Reply) actorSystem.invoke(name, "setLanguage", request, Reply.class);
                String response = reply.getResponse();

                return Mono.just(ResponseEntity
                        .ok()
                        .contentType(MediaType.TEXT_PLAIN)
                        .body(response));
            case "robert":
                rabbitMQProducer.send(request);
                return Mono.just(ResponseEntity.ok().build());
            default:
                return Mono.just(ResponseEntity.ok().build());
        }
    }

}
