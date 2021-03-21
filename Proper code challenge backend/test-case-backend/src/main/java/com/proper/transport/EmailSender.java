package com.proper.transport;

public interface EmailSender {
  void send(String to, String from, String text) throws EmailException;
}
