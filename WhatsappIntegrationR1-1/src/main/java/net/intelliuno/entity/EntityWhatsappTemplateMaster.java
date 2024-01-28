package net.intelliuno.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name="whatsapptemplatemaster_wtm")
public class EntityWhatsappTemplateMaster {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_wtm")
	private Long id;
	
	private String templateMessageWtm;
	
	private String templateNameWtm;
	
	@Column(length = 500) // Adjust the length as needed
	private String authorizationKeyWtm;
	
	private String deleteflagMessageWtm;
	
	private String messageTypeWtm;
	
	@Column(length = 225)
	private String broadcasteTypeWtm;
	



}
