package com.neoguri.neogurinest.api.presentation

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/health-check")
@RestController
class HealthCheckController {

    @GetMapping("")
    fun healthCheck(): ResponseEntity<String> {
        return ResponseEntity.ok("I'm Healthy")
    }
}
