package com.proper.domain;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Sendbroadcast {

	private DatagramSocket socket;
	private InetAddress address;
	private int port;
	private byte[] buf;

	public Sendbroadcast() throws SocketException, UnknownHostException {
		socket = new DatagramSocket();
		address = InetAddress.getByName("255.255.255.255");
		port = 4445;
	}

	public void broadcast(String Message) throws IOException  {

		//Send Message
		buf = Message.getBytes();
		DatagramPacket packet = new DatagramPacket(buf, buf.length, address, port);
		socket.setBroadcast(true);
		socket.send(packet);
		
//		//Receive answer
//		packet = new DatagramPacket(buf, buf.length);
//		socket.receive(packet);
//		String received = new String(packet.getData(), 0, packet.getLength());
//		return received;

	}

	public void close() {
		socket.close();
	}

}