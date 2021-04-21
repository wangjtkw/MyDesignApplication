package com.example.mydesignapplication.netty

object MessageFactory {
    fun getMessage(type: String?, body: String?): MessageProto.Message {
        val builder = MessageProto.Message.newBuilder()
        builder.setMsgType(type).body = body
        return builder.build()
    }

    fun getMessage(type: String?, clientId: Int, body: String?): MessageProto.Message {
        val builder = MessageProto.Message.newBuilder()
        builder.setMsgType(type).setClientID(clientId).body = body
        return builder.build()
    }

    fun getMessage(
        type: String?,
        clientId: Int,
        receiveId: Int,
        body: String?
    ): MessageProto.Message {
        val builder = MessageProto.Message.newBuilder()
        builder.setMsgType(type).setClientID(clientId).setReceiveId(receiveId).body = body
        return builder.build()
    }
}