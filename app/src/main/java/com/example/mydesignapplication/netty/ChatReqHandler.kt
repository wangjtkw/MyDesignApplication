package com.example.mydesignapplication.netty

import android.util.Log
import android.util.TimeUtils
import com.example.mydesignapplication.ui.message.ChattingBean
import com.example.mydesignapplication.utils.TimeUtil
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
                    val msg = message.body
                    val chattingBean =
                        ChattingBean(
                            clientId,
                            receiveId,
                            msg,
                            TimeUtil.getCurrentTimestamp(),
                            false
                        )
                    TCPDataSource.saveMessage(chattingBean)
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