package com.natvpsbot.command

import com.natvpsbot.annotation.Command
import com.natvpsbot.bot.BotApp
import com.natvpsbot.bot.ICommandBase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update


/**
 * 一个简单的命令示例
 */
@Service
@Command(value = "/dzurl", desc = "生成Discuz论坛可识别的超链接")
class Dzurl : ICommandBase {

    @Autowired
    private lateinit var bot: BotApp

    override fun handleCommand(update: Update) {
        val text = update.message.text
        val chatId = update.message.chatId

        val command = text.split(" ")
        if (command.size != 2) {
            val message = SendMessage()
            message.setChatId(chatId)
            message.text = "命令错误，正确格式为：/dzurl 链接"
            message.allowSendingWithoutReply = true
            bot.execute(message)
            return
        }

        val res = "[url][url][url]${command[1]}[/url][/url][/url]"
        bot.sendMessage(chatId, res)
    }

}
