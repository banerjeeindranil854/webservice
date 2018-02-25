package org.indranil.webservice.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.indranil.webservice.messenger.database.DatabaseClass;
import org.indranil.webservice.messenger.exception.DataNotFoundException;
import org.indranil.webservice.messenger.model.Message;

public class MessageService {

	private Map<Long, Message> messages = DatabaseClass.getMessages();
	
	public MessageService() {
		messages.put(1L, new Message(1, "Message 1", "indranil"));
		messages.put(2L, new Message(2, "Message 2", "indranil"));
	}
	
	public List<Message> getAllMessages() {
		return new ArrayList<Message>(messages.values()); 
	}
	
	public List<Message> getAllMessagesForYear(int year) {
		List<Message> messagesForYear = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		if(messagesForYear.isEmpty()) {
			throw new DataNotFoundException("message list is empty");
		}
		
		for (Message message : messages.values()) {
			cal.setTime(message.getCreated());
			if (cal.get(Calendar.YEAR) == year) {
				messagesForYear.add(message);
			}
		}
		return messagesForYear;
	}
	
	public List<Message> getAllMessagesPaginated(int start, int size) {
		ArrayList<Message> list = new ArrayList<Message>(messages.values());
		if(list.isEmpty()) {
			throw new DataNotFoundException("message list is empty");
		}
		if (start + size > list.size()) return new ArrayList<Message>();
		return list.subList(start, start + size); 
	}
	
	
	public Message getMessage(long id) {
		Message message = messages.get(id);
		if (message == null) {
			throw new DataNotFoundException("Message with id " + id + " not found");
		}
		return message;
	}
	
	public Message addMessage(Message message) {
		message.setId(messages.size() + 1);
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message updateMessage(Message message) {
		
		if (message.getId() <= 0) {
				throw new DataNotFoundException("issue with massage body");
			}
		messages.put(message.getId(), message);
		return message;
	}
	
	public Message removeMessage(long id) {
		return messages.remove(id);
	}
	

	
	
	
}
