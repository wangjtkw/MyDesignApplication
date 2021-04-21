package com.example.mydesignapplication.netty

import android.util.Log
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.handler.timeout.IdleState
import io.netty.handler.timeout.IdleStateEvent


class HeartBeatReqHandler : ChannelInboundHandlerAdapter() {
    private val TAG = "HeartBeatReqHandler"

    @Throws(Exception::class)
    override fun channelRead(ctx: ChannelHandlerContext, msg: Any) {
        val message = msg as MessageProto.Message
        if (message == null) {
            super.channelRead(ctx, msg)
        } else if (Constant.PING == message.msgType) {
            if (Constant.PONG_STR == message.body) {
                ctx.writeAndFlush(MessageFactory.getMessage(Constant.PING, Constant.PING_STR))
            } else if (Constant.OK_STR == message.body) {
                Log.d(TAG,"心跳续约")
            } else {
                ctx.fireChannelRead(msg)
            }
        } else {
            ctx.fireChannelRead(msg)
        }
    }

    @Throws(Exception::class)
    override fun channelActive(ctx: ChannelHandlerContext) {
        //直接跳转到下个handler请求
        ctx.fireChannelActive()
    }

    @Throws(Exception::class)
    override fun channelInactive(ctx: ChannelHandlerContext) {
        //退出登录
//        UserChannelUtil.unBindUser(ctx.channel());
//        LoginUtil.logoOut(ctx.channel());
//        System.out.Log.d(TAG,"客户端已关闭");
    }

    @Throws(Exception::class)
    override fun userEventTriggered(ctx: ChannelHandlerContext, evt: Any) {
        super.userEventTriggered(ctx, evt)
        if (evt is IdleStateEvent) {
            val event = evt
            if (event.state() == IdleState.READER_IDLE) {
                Log.d(TAG,"客户端读超时")
            } else if (event.state() == IdleState.WRITER_IDLE) {
                Log.d(TAG,"客户端写超时")
            } else if (event.state() == IdleState.ALL_IDLE) {
                Log.d(TAG,"客户端所有操作超时")
            }
            ctx.writeAndFlush(MessageFactory.getMessage(Constant.PING, Constant.PING_STR))
        }
    }
}