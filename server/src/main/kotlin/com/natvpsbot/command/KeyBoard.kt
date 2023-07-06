package com.natvpsbot.command

import com.natvpsbot.annotation.Command
import com.natvpsbot.bot.BotApp
import com.natvpsbot.bot.ICommandBase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow

@Service
@Command(value = "/settings", desc = "设置")
class KeyBoard : ICommandBase {

    @Autowired
    private lateinit var bot: BotApp

    override fun handleCommand(update: Update) {
        val message = SendMessage()
        message.chatId = update.message.chatId.toString()
        message.text = "请选择："
        val keyboardMarkup = ReplyKeyboardMarkup()
        keyboardMarkup.selective = true
        keyboardMarkup.resizeKeyboard = true
        keyboardMarkup.oneTimeKeyboard = true
        val keyboardRows: MutableList<KeyboardRow> = ArrayList()
        var row = KeyboardRow()
        row.add("a")
        keyboardRows.add(row)

        row = KeyboardRow()
        row.add("b")
        keyboardRows.add(row)

        keyboardMarkup.keyboard = keyboardRows
        message.replyMarkup = keyboardMarkup
        bot.sendMessage(message)
    }

}
