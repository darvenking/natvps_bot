package com.natvpsbot

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
@EnableScheduling
class NatVpsBotApplication

fun main(args: Array<String>) {
    runApplication<NatVpsBotApplication>(*args)
}
