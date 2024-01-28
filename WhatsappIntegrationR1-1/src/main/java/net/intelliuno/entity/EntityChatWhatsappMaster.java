package net.intelliuno.entity;



import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="whatsappchatmaster_wac")
public class EntityChatWhatsappMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String ticketIdWac;
	
	private String eventTypeWac;
	
	private String receivedIdWac;		
	
    private String conversationIdWac;
    
    private String whatsappMessageIdWac;
    
    @Column(length = 900)
    private String waIdWac;        //phone Number
    
    @Column(length = 900)
    private String textMessageWac;
    
    @Column(length = 900,name="listofticketWac")
    private String listofticketWac;
    
    
    private String templateNameWac;
    
    private String templateIdWac;
    
    private String sendFromTemplateWac;
    
    private String sendFromWatiSessionWac;
    
    private String senderReceiverWac;
    
    private String senderIdWac;
    
    private String engineerIdWac;
    
    private String uniqueKeyConnectionWac;
    
    private String idConnectionWac;
    
    private String modelIdWac;
    private String phoneNumberWac;
    
    private String statusStringWac;
    
    @CreationTimestamp
	@Column(name="createdatetime_wac")
	private LocalDateTime createdatetimeEsm;
	
	@UpdateTimestamp
	@Column(name="changedatetime_wac")
	private LocalDateTime changedDatedEsm;
	
	private String repliedOrNotWac;
	
	@Column(columnDefinition = "VARCHAR(255) DEFAULT 'N'")
	private String deleteflagWac;
	
	private String seenOrNotByTeam;
	
	private String ticketIdFromWati;
}
