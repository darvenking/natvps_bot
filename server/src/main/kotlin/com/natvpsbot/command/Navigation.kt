package com.natvpsbot.command

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.natvpsbot.annotation.CallbackPath
import com.natvpsbot.annotation.Command
import com.natvpsbot.bot.BotApp
import com.natvpsbot.bot.ICommandBase
import com.natvpsbot.domain.SiteType
import com.natvpsbot.domain.WebSite
import com.natvpsbot.mapper.SiteTypeMapper
import com.natvpsbot.mapper.WebSiteMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton
import java.util.stream.Stream

@Service
@Command(value = "/nav", desc = "网站导航")
class Navigation : ICommandBase {

    @Autowired
    private lateinit var bot: BotApp

    @Autowired
    private lateinit var webSiteMapper: WebSiteMapper

    @Autowired
    private lateinit var siteTypeMapper: SiteTypeMapper

    @CallbackPath
    val PRE_BUTTON = "上一页"

    @CallbackPath
    val NEXT_BUTTON = "下一页"

    @CallbackPath
    val BACK_BUTTON = "返回"

    @CallbackPath
    val ERROR_TEXT = "返回目录"

    override fun handleCommand(update: Update) {
        val chatId = update.message.chatId
        val message = SendMessage()
        message.setChatId(chatId)
        message.enableMarkdownV2(true)
        getTypeList(message)
        bot.sendMessage(message)
        val url = WebSite()
        url.name = "test"
        url.url = "test"
        url.type = 1
        webSiteMapper.insert(url)
    }

    override fun handleQuery(update: Update) {
        val callbackQuery = update.callbackQuery
        val messageId = callbackQuery.message.messageId.toLong()
        val chatId = callbackQuery.message.chatId
        val callbackData = callbackQuery.data
        if (callbackData == NEXT_BUTTON) {
            val text = "下一页"
            bot.sendEditMessageText(messageId.toInt(), chatId, text)
        } else if (callbackData == PRE_BUTTON) {
            val text = "上一页"
            bot.sendEditMessageText(messageId.toInt(), chatId, text)
        }
    }


    private fun getTypeList(sendMessage: SendMessage): SendMessage {
        val page = Page<SiteType>(1, 10)
        val typeList = siteTypeMapper.selectPage(
            page,
            QueryWrapper<SiteType>().eq("status", 1)
        )

        val markupInline = InlineKeyboardMarkup()
        val all: MutableList<List<InlineKeyboardButton>> = ArrayList()

        var text = "请选择分类: \n"
        var itemLine: MutableList<InlineKeyboardButton> = ArrayList()
        for ((index, siteType) in typeList.records.withIndex()) {
            if ((index + 1) % 5 == 0) {
                all.add(itemLine)
                itemLine = ArrayList()
            }
            val itemButton = InlineKeyboardButton()
            itemButton.text = siteType.name!!
            itemButton.callbackData = siteType.id.toString()
            itemLine.add(itemButton)
        }
        all.add(itemLine)
        val pageLine: MutableList<InlineKeyboardButton> = ArrayList()
        val preButton = InlineKeyboardButton()
        preButton.text = PRE_BUTTON
        preButton.callbackData = PRE_BUTTON
        pageLine.add(preButton)
        Stream.iterate(1, { i -> i + 1 }).limit(page.pages).forEach { i ->
            val pageButton = InlineKeyboardButton()
            pageButton.text = i.toString()
            pageButton.callbackData = i.toString()
            pageLine.add(pageButton)
        }
        val nextButton = InlineKeyboardButton()
        nextButton.text = PRE_BUTTON
        nextButton.callbackData = NEXT_BUTTON
        pageLine.add(nextButton)
        all.add(pageLine)

        val adLine: MutableList<InlineKeyboardButton> = ArrayList()
        val adButton = InlineKeyboardButton()
        adButton.text = "[Ad]\uD83D\uDC5C\uD83D\uDC5C\uD83D\uDC5C"
        adButton.callbackData = BACK_BUTTON
        adLine.add(adButton)
        all.add(adLine)

        val backupLine: MutableList<InlineKeyboardButton> = ArrayList()
        val backupButton = InlineKeyboardButton()
        backupButton.text = "返回"
        backupButton.callbackData = BACK_BUTTON
        backupLine.add(backupButton)
        all.add(backupLine)
        markupInline.keyboard = all
        sendMessage.replyMarkup = markupInline
        sendMessage.text = text
        return sendMessage;
    }
}
