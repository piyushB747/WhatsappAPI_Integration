package net.intelliuno.service;

public interface ServiceSendNotification {

	public abstract void  sendNotificationToScreen(String phoneNumber, String engId, String message,String conversationId);
	
}
