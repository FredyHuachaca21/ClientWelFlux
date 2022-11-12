package com.example.WebFlux.service;

import com.example.WebFlux.model.Client;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IClientService {

    Mono<Client> registrar(Client client);
    Mono<Client> modificar(Client client);
    Flux<Client> listar();
    Mono<Client> listarPorId(String id);
    Mono<Void> eliminar(String id);

}
