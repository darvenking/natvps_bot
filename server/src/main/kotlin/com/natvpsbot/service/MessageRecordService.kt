package com.natvpsbot.service

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl
import com.natvpsbot.domain.MessageRecord
import com.natvpsbot.mapper.MessageRecordMapper
import org.springframework.stereotype.Service

/**
 * @author Created by 😁JL😁
 * @since : 2023/6/8.
 */
@Service
class MessageRecordService : ServiceImpl<MessageRecordMapper, MessageRecord>()
