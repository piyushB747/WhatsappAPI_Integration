package net.intelliuno.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;
@Data
@Entity
@Table(name="watiresponse_wr")
public class EntityWatiResponseMaster {

	@Id
	@GeneratedValue(strategy =GenerationType.IDENTITY )
	@Column(name="id_wr")
	private Long id;
	
    @Lob // Use @Lob to indicate a large object (LONGTEXT in MySQL)
	@Column(name="response_wr",columnDefinition = "LONGTEXT")
	private String responseWr;
	
	@Column(name="engnameWr")
	private String engnameWr;
	
	@Column(name="deleteflag_wr")
	private String deleteflagWr="N";
	
	private String modelidWr;
	
	@Column(name="engphoneWr")
	private String engphoneWr;
	
	@CreationTimestamp
	private LocalDateTime creationTime;
}
