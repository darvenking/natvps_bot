package com.natvpsbot.command

import com.natvpsbot.annotation.CallbackPath
import com.natvpsbot.annotation.Command
import com.natvpsbot.bot.BotApp
import com.natvpsbot.bot.ICommandBase
import com.natvpsbot.mapper.WebSiteMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton

@Service
@Command(value = "/nav_manage", desc = "网站导航配置(仅管理员私聊机器人可用)")
class NavigationManage : ICommandBase {

    @Autowired
    private lateinit var bot: BotApp

    @Autowired
    private lateinit var webSiteMapper: WebSiteMapper

    @CallbackPath
    val WEB_BUTTON = "WEB_BUTTON"

    @CallbackPath
    val TYPE_BUTTON = "TYPE_BUTTON"


    override fun handleCommand(update: Update) {
        val message = SendMessage()
        val chatId = update.message.chatId
        message.setChatId(chatId)
        if (update.message.isGroupMessage) {
            message.text = "请私聊机器人使用该命令."
            bot.sendMessage(message)
            return
        }

        message.text = "请选择："
        val markupInline = InlineKeyboardMarkup()
        val rowsInLine: MutableList<List<InlineKeyboardButton>> = ArrayList()
        val rowInLIne: MutableList<InlineKeyboardButton> = ArrayList()
        val yesButton = InlineKeyboardButton()
        yesButton.text = "网址管理"
        yesButton.callbackData = WEB_BUTTON
        rowInLIne.add(yesButton)

        val rowInLIne2: MutableList<InlineKeyboardButton> = ArrayList()
        val yesButton2 = InlineKeyboardButton()
        yesButton2.text = "分类管理"
        yesButton2.callbackData = TYPE_BUTTON
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
        if (callbackData == WEB_BUTTON) {
            val text = "下一页"
            bot.sendEditMessageText(messageId.toInt(), chatId, text)
        } else if (callbackData == TYPE_BUTTON) {
            val text = "上一页"
            bot.sendEditMessageText(messageId.toInt(), chatId, text)
        }
    }

}
