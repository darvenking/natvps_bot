package com.natvpsbot.bot

import cn.hutool.core.util.IdUtil
import com.natvpsbot.annotation.Command
import com.natvpsbot.config.BotConfig
import com.natvpsbot.domain.MessageRecord
import com.natvpsbot.event.OnMemberAddGroup
import com.natvpsbot.service.MessageRecordService
import com.natvpsbot.utils.LogUtil.Companion.log
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import lombok.extern.slf4j.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.telegram.telegrambots.bots.DefaultBotOptions
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodBoolean
import org.telegram.telegrambots.meta.api.methods.botapimethods.BotApiMethodMessage
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.meta.api.objects.Message
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.exceptions.TelegramApiException
import java.util.*
import kotlin.reflect.full.findAnnotation

@Slf4j
class BotApp(options: DefaultBotOptions?, botToken: String?) : TelegramLongPollingBot(options, botToken) {
    @Autowired
    private lateinit var botConfig: BotConfig

    @Lazy
    @Autowired
    private lateinit var memberAddGroup: OnMemberAddGroup

    @Lazy
    @Autowired
    private lateinit var messageRecordService: MessageRecordService

    override fun getBotUsername(): String {
        return botConfig.botName
    }


    override fun onUpdateReceived(update: Update) {
        if (update.hasChatJoinRequest()) {
            memberAddGroup.handle(update)
        }
        if (update.hasMessage() && update.message.hasText()) {
            saveMsg(update);

            val messageText = update.message.text
            val chatId = update.message.chatId
            val c = Router.ROUTE[messageText.split(" ")[0]] ?: return
            c::class.findAnnotation<Command>()?.let {
                if (it.onlyAdmin && botConfig.ownerId.toLong() == update.message.from.id) {
                    var message = sendReplyMessage(update.message.messageId, chatId, "对不起,您不是管理员！")
                    runBlocking {
                        delay(2000)
                        val d = DeleteMessage()
                        d.chatId = chatId.toString()
                        d.messageId = message!!.messageId
                        sendMessage(d)
                        d.messageId = update.message.messageId
                        sendMessage(d)
                    }
                    return
                }
            }
            c.handleCommand(update)

        } else if (update.hasCallbackQuery()) {
            val callbackData = update.callbackQuery.data
            Router.CALLBACKQUERY[callbackData]!!.handleQuery(update)
        }
    }

    private fun saveMsg(update: Update) {
        val msg = update.message

        val message = MessageRecord()
        message.id = IdUtil.getSnowflakeNextId()
        message.tgMsgId = msg.messageId.toLong()
        message.tgUserId = msg.from.id
        message.tgUserName = msg.from.userName
        message.tgUserNickName = msg.from.firstName + msg.from.lastName
        message.content = msg.text
        message.createAt = Date()

        messageRecordService.save(message);
    }

    public fun sendMessage(chatId: Long, textToSend: String): Message? {
        return sendReplyMessage(null, chatId, textToSend)
    }

    public fun sendReplyMessage(messageId: Int?, chatId: Long, textToSend: String): Message? {
        val message = SendMessage()
        message.setChatId(chatId)
        message.replyToMessageId = messageId
        message.text = textToSend
        return sendMessage(message)
    }

    public fun sendEditMessageText(messageId: Int, chatId: Long, text: String) {
        val message = EditMessageText()
        message.chatId = chatId.toString()
        message.text = text
        message.messageId = messageId
        try {
            execute(message)
        } catch (e: TelegramApiException) {
            log.error(e.message)
        }
    }

    public fun sendMessage(message: BotApiMethodMessage): Message? {
        try {
            return execute(message)
        } catch (e: TelegramApiException) {
            log.error(e.message)
            return null;
        }
    }

    public fun sendMessage(message: BotApiMethodBoolean): Boolean {
        try {
            return execute(message)
        } catch (e: TelegramApiException) {
            log.error(e.message)
            return false;
        }
    }
}
