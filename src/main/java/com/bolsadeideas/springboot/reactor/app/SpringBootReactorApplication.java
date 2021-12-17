package com.bolsadeideas.springboot.reactor.app;

import com.bolsadeideas.springboot.reactor.app.models.Usuario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import reactor.core.publisher.Flux;

import java.util.Locale;

@SpringBootApplication
public class SpringBootReactorApplication implements CommandLineRunner {

    private static final Logger Log = LoggerFactory.getLogger(SpringBootReactorApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SpringBootReactorApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        Flux<Usuario> names = Flux.just("Daniel","Pepe","Matthew","Michelle","Samira")
                .map( nombre -> new Usuario(nombre.toUpperCase(), null))
                .doOnNext(usuario  -> {
                    if(usuario == null){
                        throw  new RuntimeException("Nombres no pueden ser vacíos");
                    }
                        System.out.println( usuario.getNombre() );
                })
                        .map( usuario -> {
                            String nombre =  usuario.getNombre().toLowerCase();
                            usuario.setNombre(nombre);
                            return usuario;
                        });

        names.subscribe(response -> Log.info(response.toString()),
                error -> Log.error(error.getMessage()),
                new Runnable() {
                    @Override
                    public void run() {
                        Log.info("Ha finalizado la ejecución del observable con éxito");
                    }
                });
    }
}
