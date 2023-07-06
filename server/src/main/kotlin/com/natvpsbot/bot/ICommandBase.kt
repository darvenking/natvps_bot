package com.natvpsbot.bot

import org.telegram.telegrambots.meta.api.objects.Update


/**
 * 每一个命令都需要实现这个接口
 */
interface ICommandBase {

    /**
     * 命令处理入口
     */
    fun handleCommand(update: Update)

    /**
     * 回调处理入口
     */
    fun handleQuery(update: Update) {}
}
