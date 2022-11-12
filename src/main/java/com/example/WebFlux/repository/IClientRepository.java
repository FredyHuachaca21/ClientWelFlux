package com.example.WebFlux.repository;

import com.example.WebFlux.model.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface IClientRepository extends ReactiveMongoRepository<Client, String> {

}
