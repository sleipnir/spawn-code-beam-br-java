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
}
