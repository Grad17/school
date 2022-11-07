package ru.hogwarts.schoolmaven.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

@Service
public class InfoService {

    @Value("${server.port}")
    private String port;

    public String getPort(){
        return port;
    }

    public int getSum(){
        return Stream.iterate(1, a -> a +1)
                .limit(1_000_000)
                .reduce(0, Integer::sum);
    }
}
