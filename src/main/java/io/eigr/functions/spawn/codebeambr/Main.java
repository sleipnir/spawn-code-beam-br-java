package io.eigr.functions.spawn.codebeambr;

import io.eigr.spawn.springboot.starter.autoconfigure.EnableSpawn;
import lombok.extern.log4j.Log4j2;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Log4j2
@EnableSpawn
@EnableRabbit
@SpringBootApplication
@EntityScan("io.eigr.functions.spawn.codebeam.br")
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);}

    /*
    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            SpawnSystem actorSystem = ctx.getBean(SpawnSystem.class);
            //Producer producer = ctx.getBean(Producer.class);
            log.info("Let's invoke some Actor");
            for (int i = 0; i < 30000; i++) {
                Request request = Request.newBuilder()
                        .setLanguage(String.format("test-language-%s", i))
                        .build();

                //producer.send(request);


                Instant initialInvokeRequestTime = Instant.now();

                Reply reply = (Reply) actorSystem.invoke("robert", "setLanguage", request, Reply.class);

                log.info("Actor invoke Sum Actor Action value result: {}. Request Time Elapsed: {}ms",
                        reply.getResponse(), Duration.between(initialInvokeRequestTime, Instant.now()).toMillis());
            }
        };
    }
     */

}
