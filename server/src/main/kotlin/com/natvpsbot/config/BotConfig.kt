package com.natvpsbot.config

import lombok.Data
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.scheduling.annotation.EnableScheduling

@Configuration
@Data
@EnableScheduling
@PropertySource("application.yml")
class BotConfig {
    @Value("\${bot.api}")
    public lateinit var api: String

    @Value("\${bot.name}")
    public lateinit var botName: String

    @Value("\${bot.token}")
    public lateinit var token: String

    @Value("\${bot.owner}")
    public lateinit var ownerId: String
}
