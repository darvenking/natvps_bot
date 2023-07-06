package com.natvpsbot.scheduled

import com.natvpsbot.bot.BotApp
import com.natvpsbot.utils.RedisUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


/**
 * @author Created by 😁JL😁
 * @since : 2023/6/8.
 */
@Component
class PostPush {

    @Autowired
    private lateinit var bot: BotApp

    @Autowired
    private lateinit var redisUtil: RedisUtil

    private val KEY = "tid:"

    //    @Scheduled(cron = "*/10 * * * * ?")
//    @Scheduled(cron = "* */5 * * * ?")
    fun push() {

    }

}
