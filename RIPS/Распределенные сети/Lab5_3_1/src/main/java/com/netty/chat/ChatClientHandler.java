package com.netty.chat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ChatClientHandler extends SimpleChannelInboundHandler<String> {

	/*
	 * Print chat message received from server.
	 */
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, String msg) {
		System.out.println("Message: " + msg);
	}
}
