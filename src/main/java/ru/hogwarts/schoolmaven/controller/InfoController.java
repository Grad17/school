package ru.hogwarts.schoolmaven.controller;

import org.springframework.web.bind.annotation.GetMapping;
import ru.hogwarts.schoolmaven.service.InfoService;

public class InfoController {

    private final InfoService infoService;


    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping
    public String getPort(){
        return infoService.getPort();
    }

    @GetMapping("get_sum")
    public int getSum(){
        return infoService.getSum();
    }
}
