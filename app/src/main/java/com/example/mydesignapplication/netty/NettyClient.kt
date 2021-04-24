package com.example.mydesignapplication.netty

import android.util.Log
import com.example.mydesignapplication.ui.MainActivity
import com.example.mydesignapplication.ui.message.ChattingBean
import com.example.mydesignapplication.utils.TimeUtil
import com.example.mydesignapplication.utils.ToastUtil
import io.netty.bootstrap.Bootstrap
import io.netty.channel.Channel
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.codec.protobuf.ProtobufDecoder
import io.netty.handler.codec.protobuf.ProtobufEncoder
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import io.netty.handler.timeout.IdleStateHandler
import io.netty.util.concurrent.Future
import java.util.*
import java.util.concurrent.TimeUnit

class NettyClient {
    var group: EventLoopGroup = NioEventLoopGroup()
    fun connect(port: Int, host: String) {

        //client NIO thread
        try {
            val b = Bootstrap()
            b.group(group).channel(NioSocketChannel::class.java)
                .option(ChannelOption.TCP_NODELAY, true) //日志打印
                .handler(LoggingHandler(LogLevel.INFO))
                .handler(object : ChannelInitializer<Channel>() {
                    @Throws(Exception::class)
                    override fun initChannel(ch: Channel) {
                        //设置超时时间
                        ch.pipeline().addLast(IdleStateHandler(10, 10, 10, TimeUnit.SECONDS))
                        ch.pipeline().addLast(ProtobufVarint32FrameDecoder())
                        ch.pipeline()
                            .addLast(ProtobufDecoder(MessageProto.Message.getDefaultInstance()))
                        ch.pipeline().addLast(ProtobufVarint32LengthFieldPrepender())
                        ch.pipeline().addLast(ProtobufEncoder())
                        //心跳续约
                        ch.pipeline().addLast(HeartBeatReqHandler())
                        //登录校验
                        ch.pipeline().addLast(LoginAuthReqHandler())
                        ch.pipeline().addLast(ChatReqHandler())
                    }
                })
            //异步连接
            connect(b, host, port, MAX_RETRY)
        } finally {
            //优雅退出
            group.shutdownGracefully()
        }
    }

    companion object {
        private const val MAX_RETRY = 5 //最大重连次数
        var channel: Channel? = null
        private const val TAG = "NettyClient"

        fun connect(host: String, port: Int) {
            Thread { NettyClient().connect(port, host) }.start()
        }

        fun sendMessage(userId: Int, message: String) {
            if (channel == null) {
                ToastUtil.makeToast("出现错误！")
                return
            }
            val employerId = MainActivity.EMPLOYER_ACCOUNT_BEAN!!.employerAccountId
            channel!!.writeAndFlush(
                MessageFactory.getMessage(
                    Constant.EMPLOYER2USER_MESSAGE,
                    employerId,
                    userId,
                    message
                )
            )
            Thread {
                val chattingBean =
                    ChattingBean(
                        userId,
                        employerId,
                        message,
                        TimeUtil.getCurrentTimestamp(),
                        true
                    )
                TCPDataSource.saveMessage(chattingBean)
            }.start()
        }

        private fun connect(bootstrap: Bootstrap, host: String, port: Int, retry: Int) {
            val future1 = bootstrap.connect(host, port).addListener { future: Future<in Void?> ->
                if (future.isSuccess) {
                    Log.d(TAG, Date().toString() + ": 连接成功!")
                } else if (retry == 0) {
                    Log.d(TAG, "重试次数已用完，放弃连接！")
                } else {
                    // 第几次重连
                    val order = MAX_RETRY - retry + 1
                    // 本次重连的间隔
                    val delay = 1 shl order
                    Log.d(TAG, Date().toString() + ": 连接失败，第" + order + "次重连")
                    bootstrap.group().schedule(
                        { connect(bootstrap, host, port, retry - 1) },
                        delay.toLong(),
                        TimeUnit.SECONDS
                    )
                }
            }
            try {
                future1.channel().closeFuture().sync()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
    }
}