package com.luxoft.springadvanced.webflux.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractReactiveMongoConfiguration;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;

@Configuration
@EnableReactiveMongoRepositories(basePackages = "com.luxoft.springadvanced.webflux.dao")
@RequiredArgsConstructor
public class MongoConfig extends AbstractReactiveMongoConfiguration {

  @Value("${mongodb.port}")
  String port;

  @Getter
  @Value("${mongodb.dbname}")
  String databaseName;

  @Override
  public MongoClient reactiveMongoClient() {
    return MongoClients.create();
  }

  @Bean
  public ReactiveMongoTemplate reactiveMongoTemplate() {
    return new ReactiveMongoTemplate(reactiveMongoClient(), getDatabaseName());
  }
}
