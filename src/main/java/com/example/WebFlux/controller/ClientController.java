package com.example.WebFlux.controller;

import com.example.WebFlux.model.Client;
import com.example.WebFlux.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private IClientService service;

    @GetMapping()
    public Mono<ResponseEntity<Flux<Client>>> listar(){
        Flux<Client> clientFlux = service.listar();
        return Mono.just(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(clientFlux))
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

    @PostMapping
    public Mono<ResponseEntity<Client>> registrar(@RequestBody Client client){
        return service.registrar(client)
                .map(newClient ->
                        ResponseEntity.ok()
                                .contentType(MediaType.APPLICATION_JSON)
                                .body(newClient))
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

    @GetMapping("/{id}")
public Mono<ResponseEntity<Client>> buscarPorId(@PathVariable(value = "id") String id){
        return service.listarPorId(id)
                .map(client -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(client))
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Client>> actualizar(@RequestBody Client client, @PathVariable("id") String id ){
        Mono<Client> clientMono = Mono.just(client);
        Mono<Client> clientDB = service.listarPorId(id);


        return clientDB
                .zipWith(clientMono, (db, c)->{
                    db.setId(id);
                    db.setName(c.getName());
                    return db;
                })
                .flatMap(x -> service.modificar(x))
                .map(cli ->
                    ResponseEntity.ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(cli)
                )
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NO_CONTENT));

    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> eliminarClient(@PathVariable(value = "id") String id){
        return service.eliminar(id)
                .map(cliDelete -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(cliDelete))
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

}
