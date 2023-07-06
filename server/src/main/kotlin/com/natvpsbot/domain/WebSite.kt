package com.natvpsbot.domain

import com.fasterxml.jackson.annotation.JsonFormat
import lombok.Data
import java.util.*

/**
 * @author Created by 😁JL😁
 * @since : 2023/5/10.
 */
@Data
class WebSite {
    var id: Long? = null
    var name: String? = null
    var url: String? = null
    var type: Int? = null
    var status: Int? = null

    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    var createAt: Date? = null
}
