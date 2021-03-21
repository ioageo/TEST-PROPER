package com.proper.transport;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;


import com.proper.events.EmailSended;


@SuppressWarnings({  "rawtypes" })
public class EventPublisherClass<T> implements EventPublisher {

	private Queue<Object> ToPublish;
	private List<EmailSended> BeenPublished;

	public EventPublisherClass() {
		ToPublish = new LinkedList<>();
		BeenPublished = new ArrayList<>();
	}


	@Override
//	poll	From	EventPublisherQueue
	public Object poll() {
		// TODO Auto-generated method stub
		return null ;
	}

	@Override
	public void put(Object Object) {
		ToPublish.add(Object );
	}
	public void put(List<Object> Object) {
		ToPublish.addAll(Object);
	}
/**
 * Sending all the mails from the eventList
 */
	public void publish() {
		String to = "propertestioannis@gmail.com";
		String from = "propertestioannis@gmail.com";
		String text = "";

		EmailSenderClass emailSender = new EmailSenderClass();
		while (!ToPublish.isEmpty()) {
			Object ce = ToPublish.poll() ;
			text =  ce.toString();
			//Send mail
			emailSender.send(to, from, text);
			
			//Save the text,to
			EmailSended es = new EmailSended();
			es.setEmailTo(to);
			es.setText(text);
			BeenPublished.add(es);
		}
	}

	public List<EmailSended> getBeenPublished() {
		return BeenPublished;
	}

}
