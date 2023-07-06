package com.natvpsbot.bot

import cn.hutool.core.util.StrUtil
import com.natvpsbot.annotation.CallbackPath
import com.natvpsbot.annotation.Command
import com.natvpsbot.utils.LogUtil.Companion.log
import com.natvpsbot.utils.SpringUtils
import java.io.File

class Router {
    companion object {
        //命令路由
        val ROUTE: MutableMap<String, ICommandBase> = mutableMapOf()
        //按钮事件的回调路由
        val CALLBACKQUERY: MutableMap<String, ICommandBase> = mutableMapOf()

        fun initRouter() {
            val folder = File(object {}.javaClass.getResource("")!!.file)
            val path = folder.parentFile.path + File.separator + "command"
            listFiles(path, mutableListOf()).forEach { it1 ->
                val className = it1!!.substringAfterLast(File.separator).substringBeforeLast(".")
                val clazz = Class.forName("com.mofabala.command.$className")
                clazz.getAnnotation(Command::class.java)?.let{ it2 ->
                    val cc: ICommandBase = SpringUtils.getBean(StrUtil.lowerFirst(className))
                    ROUTE[it2.value] = cc
                }
                val fields = clazz.declaredFields
                val annotatedProperties = fields.filter { it2 -> it2.annotations.any { annotation -> annotation is CallbackPath } }
                annotatedProperties.forEach { prop ->
                    prop.isAccessible = true
                    val cc: ICommandBase = SpringUtils.getBean(StrUtil.lowerFirst(className))
                    val value = prop.get(cc)
                    CALLBACKQUERY[value.toString()] = cc
                }
            }
            log.info("Router Init Finished!")
        }

        /**
         * 递归遍历目录下文件
         */
        private fun listFiles(filePath: String, fileArr: MutableList<String>): List<String?> {
            val files = File(filePath).listFiles() ?: return fileArr
            for (k in files.indices) {
                if (files[k].isDirectory) {
                    listFiles(files[k].path, fileArr)
                } else if (!files[k].isDirectory) {
                    fileArr.add(files[k].absolutePath)
                }
            }
            return fileArr
        }
    }
}
