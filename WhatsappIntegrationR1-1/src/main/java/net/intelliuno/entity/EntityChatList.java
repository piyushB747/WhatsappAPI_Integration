package net.intelliuno.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="whatsappchatlist_wcl")
public class EntityChatList {

  @Id
  @GeneratedValue(strategy =GenerationType.IDENTITY )
  @Column(name="id_wcl")
  private Long idWcl;	
  
  private String titleWcl;
  private String messageTextWcl;
  private String phoneNumberWcl;
  private String dateAndTimeToShowWcl;
  
  @CreationTimestamp
  @Column(name="createdatetime_wac")
  private LocalDateTime createdatetimeEsm;
  
  private String deleteflagWcl;
  
  
}
