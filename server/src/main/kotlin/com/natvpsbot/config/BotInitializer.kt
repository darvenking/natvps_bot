package com.natvpsbot.config

import com.natvpsbot.annotation.Command
import com.natvpsbot.bot.BotApp
import com.natvpsbot.bot.Router
import com.natvpsbot.utils.LogUtil.Companion.log
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import kotlin.reflect.full.findAnnotation
import kotlin.system.exitProcess

@Slf4j
@Component
class BotInitializer {

    private var bot: BotApp? = null

    @Autowired
    private lateinit var botConfig: BotConfig

    @EventListener(ContextRefreshedEvent::class)
    @Throws(TelegramApiException::class)
    fun init() {
        val telegramBotsApi = TelegramBotsApi(DefaultBotSession::class.java)
        try {
            telegramBotsApi.registerBot(bot)
            Router.initRouter()
            val listOfCommands: MutableList<BotCommand> = ArrayList()
            Router.ROUTE.forEach { (key, value) ->
                value::class.findAnnotation<Command>()?.let {
                    listOfCommands.add(BotCommand(it.value, it.desc))
                }
            }
            try {
                bot!!.execute(SetMyCommands(listOfCommands, BotCommandScopeDefault(), null))
            } catch (e: TelegramApiException) {
                log.error("Error setting bot's command list: " + e.message)
            }
        } catch (e: TelegramApiException) {
            log.error("Error occurred: " + e.message)
            exitProcess(1)
        }
    }

    @Bean
    fun initBot(): BotApp {
        val options = DefaultBotOptions()
        options.baseUrl = botConfig.api + "/bot"
        bot = BotApp(options, botConfig.token)
        return bot!!
    }
}
