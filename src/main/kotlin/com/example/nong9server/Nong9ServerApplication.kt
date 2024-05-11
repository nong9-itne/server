package com.example.nong9server

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class Nong9ServerApplication

fun main(args: Array<String>) {
    runApplication<Nong9ServerApplication>(*args)
}

@RestController
class HeartBeatController {
    @GetMapping("/heartbeat")
    fun heartbeat() = "OK"
}
