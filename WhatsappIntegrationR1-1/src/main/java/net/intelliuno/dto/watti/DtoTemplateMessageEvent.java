package net.intelliuno.dto.watti;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DtoTemplateMessageEvent {

    private String eventType;
    private String id;
    private String whatsappMessageId;
    private String templateId;
    private String templateName;
    private String created;
    private String conversationId;
    private String ticketId;
    private String text;
    private String operatorEmail;
    private String waId;
    private String type;
    private String statusString;
    private String sourceType;
    private String localMessageId;
    private String timestamp;
	private String assigneeId;
	private String sourceUrl;
	private String data;
	private String sourceId;
	private String owner;
	private String avatarUrl;
	private String assignedId;
	private String operatorName;
	private String messageContact;
	private String senderName;
	private String listReply;
	private String replyContextId;
}
