package com.natvpsbot.command

import com.natvpsbot.annotation.Command
import com.natvpsbot.bot.BotApp
import com.natvpsbot.bot.ICommandBase
import com.natvpsbot.utils.LogUtil.Companion.log
import com.vdurmont.emoji.EmojiParser
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Update


@Service
@Command(value = "/start", desc = "开始")
class Start : ICommandBase {

    @Autowired
    private lateinit var bot: BotApp

    override fun handleCommand(update: Update) {
        val chatId = update.message.chatId
        val name = update.message.chat.userName
        val answer = EmojiParser.parseToUnicode("Hi, $name, nice to meet you!:blush:")
        log.info("Replied to user $name")
        bot.sendMessage(chatId, answer)
    }

}
