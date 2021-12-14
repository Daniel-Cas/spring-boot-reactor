package com.bolsadeideas.springboot.reactor.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

    private static final Logger Log = LoggerFactory.getLogger(SpringBootReactorApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootReactorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Flux<String> names = Flux.just("Daniel","Pepe","Matthew","Michelle","Samira")
                .doOnNext(e -> {
                    if(e.isEmpty()){
                        throw  new RuntimeException("Nombres no pueden ser vacíos");
                    }
                        System.out.println(e);
                })
                        .map( name -> {
                            return name.toUpperCase();
                        });

        names.subscribe(response -> Log.info(response),
                error -> Log.error(error.getMessage()),
                new Runnable() {
                    @Override
                    public void run() {
                        Log.info("Ha finalizado la ejecución del observable con éxito");
                    }
                });
    }
}
