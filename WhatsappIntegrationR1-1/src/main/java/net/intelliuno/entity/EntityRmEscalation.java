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

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="rmescalatewhatsapp_rsw",schema = "erprmwise")
public class EntityRmEscalation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_rsw")
	private Long id;
	
	private String rmNameRsw;
	
	private String rmPhonenumberRsw;
	
	private String rmIdRsw;
	
	private String messageSentRsw;
	
	private String deleteflagRsw;
	
	private String messageRepliedRsw;
	
	@CreationTimestamp
	private LocalDateTime creationRsw;
	
	@UpdateTimestamp
	private LocalDateTime updationRsw;
	
}
