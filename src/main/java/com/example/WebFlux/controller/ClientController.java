package com.example.WebFlux.controller;

import com.example.WebFlux.model.Client;
import com.example.WebFlux.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private IClientService service;

    @GetMapping()
    public Flux<Client> listar(){
        return service.listar();
    }

    @PostMapping
    public Mono<Client> registrar(@RequestBody Client client){
        return service.registrar(client);
    }

    @GetMapping("/{id}")
    public  Mono<Client> buscarPorId(@PathVariable(value = "id") String id){
        return service.listarPorId(id);
    }

    @PutMapping("/{id}")
    public Mono<Client> actualizar(@RequestBody Client client, @PathVariable("id") String id ){
        Mono<Client> clientMono = Mono.just(client);
        Mono<Client> clientDB = service.listarPorId(id);


        return clientDB
                .zipWith(clientMono, (db, c)->{
                    db.setId(id);
                    db.setName(c.getName());
                    return db;
                })
                .flatMap(x -> service.modificar(x))
                .defaultIfEmpty(client);

    }

    @DeleteMapping("/{id}")
    public Mono<Void> eliminarClient(@PathVariable(value = "id") String id){
        return service.eliminar(id);
    }

}
