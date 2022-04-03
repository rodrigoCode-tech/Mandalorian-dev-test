package org.acme.redis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.quarkus.redis.client.RedisClient;
import io.quarkus.redis.client.reactive.ReactiveRedisClient;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.redis.client.Response;

@Singleton
public class TarefasService {
    
    @Inject
    RedisClient redisCliente;
    
    @Inject
    ReactiveRedisClient reactiveRedisClient;

    Uni<Void> del (String titulo){
        return reactiveRedisClient.del(Arrays.asList(titulo))
            .map(response -> null );
    }

    String get(String titulo){
        io.vertx.redis.client.Response result = redisCliente.get(titulo);
        return result!=null ? result.toString() : null;
    }

    void set(String titulo, String anotacao){
        redisCliente.set(Arrays.asList(titulo, anotacao));
    }

    Uni<List<String>> titulo(){
        return reactiveRedisClient
                .keys("*")
                .map(response -> {
                    List<String> result = new ArrayList<>();
                    for (Response r : response) {
                        result.add(r.toString());
                    }
                    return result;
                });
                }    
    }
