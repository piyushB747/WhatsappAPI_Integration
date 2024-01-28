package net.intelliuno.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
@Data
@Entity
@Table(name="whatsapperror_we")
public class EntityWhatsappError {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_we")
	private Long id;
	
	@Column(name="errorlog_we",length = 900)
	private String errorMessageWe;
	
	@Column(name="deleteflag_we",length = 900)
	private String deleteflagWe="N";
	
	@Column(name="engname_we",length = 900)
	private String engNameWe="N";
	
	@Column(name="phone_eng_we",length = 900)
	private String phoneEng;
	
	@CreationTimestamp
	@Column(name="createdatetime_we")
	private LocalDateTime createdatetimeWe;
		
}
