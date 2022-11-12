package com.example.WebFlux.serviceImpl;

import com.example.WebFlux.model.Client;
import com.example.WebFlux.repository.IClientRepository;
import com.example.WebFlux.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ClientServiceImpl implements IClientService {
    @Autowired
    private IClientRepository repository;


    @Override
    public Mono<Client> registrar(Client client) {
        return repository.save(client);
    }

    @Override
    public Mono<Client> modificar(Client client) {
        return repository.save(client);
    }

    @Override
    public Flux<Client> listar() {
        return repository.findAll();
    }

    @Override
    public Mono<Client> listarPorId(String id) {
        return repository.findById(id);
    }

    @Override
    public Mono<Void> eliminar(String id) {
        return repository.deleteById(id);
    }
}
