package com.natvpsbot.command

import com.natvpsbot.annotation.Command
import com.natvpsbot.bot.BotApp
import com.natvpsbot.bot.ICommandBase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Update

@Service
@Command(value = "/help",desc = "帮助",onlyAdmin = true)
class Help : ICommandBase {

    val HELP_TEXT = """
            This bot is created to demonstrate Spring capabilities.

            You can execute commands from the main menu on the left or by typing a command:

            Type /start to see a welcome message 

            Type /help to see this massage again
                        
            Type /settings to set your preferences
                        
            """.trimIndent()

    @Autowired
    private lateinit var bot: BotApp

    override fun handleCommand(update: Update) {
        val chatId = update.message.chatId
        bot.sendMessage(chatId, HELP_TEXT)
    }

}
