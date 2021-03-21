package com.proper.transport;


import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;


public class EmailSenderClass implements EmailSender {
	
	@Override
	public void send(String to, String from, String text) {
		Properties properties = System.getProperties();	
		properties.setProperty("mail.transport.protocol", "smtp");     
		properties.setProperty("mail.host", "smtp.gmail.com");  
		properties.put("mail.smtp.auth", "true");  
		properties.put("mail.smtp.port", "465");  
		properties.put("mail.debug", "true");  
		properties.put("mail.smtp.socketFactory.port", "465");  
		properties.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");  
		properties.put("mail.smtp.socketFactory.fallback", "false");  

		Session session = Session.getInstance(properties, new Authenticator(){
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
			String password="G6gw6aPasba9b6J";
			return new PasswordAuthentication(from,password);}
		
		});	
			Message message = new MimeMessage(session);
		try {
			message.setFrom(new InternetAddress(from));
			message.addRecipient(RecipientType.TO, new InternetAddress(to));
			message.setText(text);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			Transport.send(message);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
}