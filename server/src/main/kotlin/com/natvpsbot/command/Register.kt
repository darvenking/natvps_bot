package com.natvpsbot.command

import com.natvpsbot.annotation.CallbackPath
import com.natvpsbot.annotation.Command
import com.natvpsbot.bot.BotApp
import com.natvpsbot.bot.ICommandBase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton

@Service
@Command(value = "/register", desc = "注册")
class Register : ICommandBase {

    @Autowired
    private lateinit var bot: BotApp

    @CallbackPath
    val YES_BUTTON = "YES_BUTTON"

    @CallbackPath
    val NO_BUTTON = "NO_BUTTON"

    @CallbackPath
    val ERROR_TEXT = "Error occurred: "

    override fun handleCommand(update: Update) {
        val chatId = update.message.chatId
        val message = SendMessage()
        message.setChatId(chatId)
        message.text = "Do you really want to register?"
        val markupInline = InlineKeyboardMarkup()
        val rowsInLine: MutableList<List<InlineKeyboardButton>> = ArrayList()
        val rowInLIne: MutableList<InlineKeyboardButton> = ArrayList()
        val yesButton = InlineKeyboardButton()
        yesButton.text = "Yes"
        yesButton.callbackData = YES_BUTTON
        rowInLIne.add(yesButton)
        val noButton = InlineKeyboardButton()
        noButton.text = "NO"
        noButton.callbackData = NO_BUTTON
        rowInLIne.add(noButton)
        val rowInLIne2: MutableList<InlineKeyboardButton> = ArrayList()
        val yesButton2 = InlineKeyboardButton()
        yesButton2.text = "Yes"
        yesButton2.callbackData = YES_BUTTON
        rowInLIne2.add(yesButton)
        rowsInLine.add(rowInLIne)
        rowsInLine.add(rowInLIne2)
        markupInline.keyboard = rowsInLine
        message.replyMarkup = markupInline
        bot.sendMessage(message)
    }

    override fun handleQuery(update: Update) {
        val callbackQuery = update.callbackQuery
        val messageId = callbackQuery.message.messageId.toLong()
        val chatId = callbackQuery.message.chatId
        val callbackData = callbackQuery.data
        if (callbackData == YES_BUTTON) {
            val text = "You pressed YES button"
            bot.sendEditMessageText(messageId.toInt(), chatId, text)
        } else if (callbackData == NO_BUTTON) {
            val text = "You pressed NO button"
            bot.sendEditMessageText(messageId.toInt(), chatId, text)
        }
    }
}
