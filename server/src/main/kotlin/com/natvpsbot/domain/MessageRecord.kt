package com.natvpsbot.domain

import com.fasterxml.jackson.annotation.JsonFormat
import lombok.Data
import java.util.*

/**
 * @author Created by 😁JL😁
 * @since : 2023/6/8.
 */
@Data
class MessageRecord {
    var id: Long? = null
    var tgMsgId: Long? = null
    var tgUserId: Long? = null
    var tgUserName: String? = null
    var tgUserNickName: String? = null
    var content: String? = null

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    var createAt: Date? = null
}
