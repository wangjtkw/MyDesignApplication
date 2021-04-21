package com.example.mydesignapplication.netty

import android.util.Log
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter

class ChatReqHandler : ChannelInboundHandlerAdapter() {
    private val TAG = "ChatReqHandler"

    @Throws(Exception::class)
    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        val message = msg as MessageProto.Message
        if (message == null) {
            super.channelRead(ctx, msg)
        } else {
            if (Constant.USER2EMPLOYER_MESSAGE == message.msgType) {
                Thread {
                    val clientId = message.clientID
                    val receiveId = message.receiveId
                    try {
                        Thread.sleep(1000)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }
                    ctx.writeAndFlush(
                        MessageFactory.getMessage(
                            Constant.EMPLOYER2USER_MESSAGE,
                            receiveId,
                            clientId,
                            System.currentTimeMillis().toString() + ":msg"
                        )
                    )
                    Log.d(TAG, "收到的消息：" + message.body)
                }.start()
            }
        }
    }

    @Throws(Exception::class)
    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
        ctx.close()
    }
}