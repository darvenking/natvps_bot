package com.natvpsbot.event

import com.natvpsbot.bot.BotApp
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.objects.Update

/**
 * @author Created by 😁JL😁
 * @since : 2023/4/17.
 */
@Service
class OnMemberAddGroup {

    @Autowired
    private lateinit var bot: BotApp

    fun handle(update: Update) {
        println("onMemberAddGroup")
        bot.sendMessage(update.message.chatId, "欢迎新人@${update.message.chat.userName}加入西柚论坛官方群!")
    }

}
