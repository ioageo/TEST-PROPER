package com.proper.events;

public class EmailSended extends Event{
	private String emailTo;
	private String Text;
	
	public String getEmailTo() {
		return emailTo;
	}
	public void setEmailTo(String emailTo) {
		this.emailTo = emailTo;
	}
	public String getText() {
		return Text;
	}
	public void setText(String text) {
		Text = text;
	}

}
