package com.natvpsbot.utils

import org.slf4j.Logger
import org.slf4j.LoggerFactory


class LogUtil {
    companion object {
        val <reified T> T.log: Logger
            inline get() = LoggerFactory.getLogger(T::class.java)
    }

}
