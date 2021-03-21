package com.proper.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Testbroacast {

	Sendbroadcast sender;
	Recievebroadcast reciever;

	@Before
	public void setup() throws SocketException, UnknownHostException {
		reciever = new Recievebroadcast();
		reciever.run();
		sender = new Sendbroadcast();
	}



	@Test
	public void whenCanSendAndReceivePacket_thenCorrect() throws IOException {
		sender.broadcast("hello server");
        assertEquals("hello server", reciever.run());
//        echo = sender.broadcast("server is working");
//        assertFalse(echo.equals("hello server"));
//        assertTrue(echo.equals("server is working"));
	}

	@After
	public void tearDown() throws IOException {
		
		sender.close();
	}
}