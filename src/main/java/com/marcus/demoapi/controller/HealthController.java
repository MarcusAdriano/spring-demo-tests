package com.marcus.demoapi.controller;

import com.marcus.demoapi.model.Health;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("health")
public class HealthController {

    @GetMapping(produces = "application/json; charset=utf-8")
    public Health health() {

        final Health health = new Health();
        health.setAvailable(true);

        return health;
    }
}
