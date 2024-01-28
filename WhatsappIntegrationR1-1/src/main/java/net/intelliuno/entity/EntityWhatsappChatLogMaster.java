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
@Table(name="whatsappchatlogmaster_wac")
public class EntityWhatsappChatLogMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
    @Column(length = 900) // Adjust the length value as needed
	private String messageWac;
	
	private String messageTypeWac;
	
	private String watiSessionMessage;
	
	private String messageIdWac;
	
	private String messageTemplateForWac;
	
	private String messageSenderWac;
	
	private String mobileNoWac;
	
	private String messageReceiverWac;
	
	private String incidentIdWac;
	
	private String assetIdWac;
	
	private String siteIdWac;
	
	private String engidWac;
	
	private String m_strAgingWac;
	
	private String m_strSetUrlForOpenWac;
	
	private String messageSentFromWati;
	
	@CreationTimestamp
	@Column(name="createdatetime_wac")
	private LocalDateTime createdatetimeEsm;
	
	@UpdateTimestamp
	@Column(name="changedatetime_wac")
	private LocalDateTime changedDatedEsm;
	
}
