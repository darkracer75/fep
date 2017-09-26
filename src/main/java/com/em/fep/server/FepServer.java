package com.em.fep.server;

import com.em.fep.codec.FepMessageDecoder;
import com.em.fep.codec.FepMessageEncoder;
import com.em.fep.codec.FepLengthBasedDecoder;
import com.em.fep.protocol.bc.BCMessageFactory;
import com.em.fep.protocol.bc.server.BCMessageServerProcessor;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
             .option(ChannelOption.SO_BACKLOG, 10)
             .childHandler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 protected void initChannel(SocketChannel ch) throws Exception {

                     ChannelPipeline p = ch.pipeline();

                     FepLengthBasedDecoder frameDecoder = new FepLengthBasedDecoder(4096, 0, 4);
                     p.addLast(frameDecoder, new StringEncoder(), new FepMessageDecoder(new BCMessageFactory()), new FepMessageEncoder(), new FepServerHandShakingHandler(new BCMessageServerProcessor()));
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
