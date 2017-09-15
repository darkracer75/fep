package com.em.fep.server;

import com.em.fep.codec.FepRequestDecoder;
import com.em.fep.codec.FepRequestEncoder;
import com.em.fep.codec.PacketLengthFieldBasedFrameDecoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

@Component
@Slf4j
public class FepServer {

    @Value("${fep.server.port:5678}")
    private int port;



    public void start() {

        log.debug("FepServer start!!");

        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .option(ChannelOption.SO_BACKLOG, 100)
             .childHandler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 protected void initChannel(SocketChannel ch) throws Exception {
                     PacketLengthFieldBasedFrameDecoder frameDecoder = new PacketLengthFieldBasedFrameDecoder(4096, 0, 4);
                     ChannelPipeline p = ch.pipeline();
                     p.addLast(frameDecoder, new StringEncoder(), new FepRequestDecoder(), new FepRequestEncoder(), new FepServerHandShakingHandler());
                 }
             });

            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }
}
