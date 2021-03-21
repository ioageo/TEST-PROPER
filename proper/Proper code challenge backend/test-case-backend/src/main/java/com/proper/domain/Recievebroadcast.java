package com.proper.domain;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


public class Recievebroadcast  {

	private DatagramSocket socket;

	private byte[] buf=new byte[256];

	public Recievebroadcast() throws SocketException {
		socket = new DatagramSocket(4445);

	}

	public String run() {
		

        String received="";
		while (received.equals("Hello server")) {
            DatagramPacket packet 
              = new DatagramPacket(buf, buf.length);
            try {
				socket.receive(packet);
			} catch (IOException e) {
				e.printStackTrace();
			}
//            
//            InetAddress address = packet.getAddress();
//            int port = packet.getPort();
//            packet = new DatagramPacket(buf, buf.length, address, port);
            received = new String(packet.getData(), 0, packet.getLength());
//            System.out.println(received);
//            if (received.contains("endmessege")) {
//            	System.out.println("entered");
//                running = false;
//                continue;
//            }
//            try {
//				socket.send(packet);
//			} catch (IOException e) {
//				e.printStackTrace();
			}
		socket.close();
		return received;
   
    }
	

	public void close() {
		socket.close();
		
	}
}