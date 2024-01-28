package net.intelliuno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.intelliuno.entity.EntityChatWhatsappMaster;
import java.util.List;
public interface RepoEntityChatWhatsappMaster extends JpaRepository<EntityChatWhatsappMaster, Long>{

	
	@Query("SELECT em FROM EntityChatWhatsappMaster em WHERE em.modelIdWac=?1 And em.ticketIdWac =?2 And em.phoneNumberWac=?3 AND em.deleteflagWac='N' ")
	public EntityChatWhatsappMaster returnIncidentDetailsForWati(String modelId,String m_strTypeid,String phoneNumber);

	
	@Query("SELECT em FROM EntityChatWhatsappMaster em WHERE em.receivedIdWac=?1 AND em.deleteflagWac='N' ORDER BY em.id DESC ")
	public EntityChatWhatsappMaster returnIncidentDetailsForWatiUpdateDevliveredReaded(String receivedIdWac);
	
	
	@Query("SELECT em FROM EntityChatWhatsappMaster em WHERE em.conversationIdWac=?1  AND em.waIdWac=?2 AND em.deleteflagWac='N' ORDER BY em.id DESC ")
	public List<EntityChatWhatsappMaster> findRepliedUserAll(String conversationIdWac,String waIdWac);
	
	@Query("SELECT em FROM EntityChatWhatsappMaster em WHERE em.conversationIdWac=?1 AND em.deleteflagWac='N' ORDER BY em.id DESC ")
	public List<EntityChatWhatsappMaster> findRepliedUser(String conversationIdWac);
	
	//@Query("SELECT em FROM EntityChatWhatsappMaster em WHERE em.deleteflagWac='N' And em.conversationIdWac!=null And em.ticketIdWac=?1 OR em.phoneNumberWac=?2  OR em.waIdWac=?3   ORDER BY em.id ASC ")
	//public List<EntityChatWhatsappMaster> findUsersByIncidentIdAndPhoneOld(String ticketIdWac,String phoneNumber,String waIdWac);
	
	
	/**
	 Select * from whatsappchatmaster_wac where deleteflag_wac='N'  and conversation_id_wac is NOT NULL and ( ticket_id_wac='30260' 
	 and wa_id_wac='7506214796') order by id asc;
	 **/
	@Query("SELECT em FROM EntityChatWhatsappMaster em WHERE em.deleteflagWac='N' AND  em.conversationIdWac IS NOT NULL"
			+ " AND (em.ticketIdWac=?1 And em.waIdWac=?2) ORDER BY em.id ASC")
	public List<EntityChatWhatsappMaster> findUsersByIncidentIdAndPhone(String ticketIdWac,  String waIdWac);
    

	
	@Query(value = "SELECT * FROM whatsappchatmaster_wac em WHERE em.conversation_id_wac = ?1 AND em.phone_number_wac = ?2 AND em.deleteflag_wac = 'N' "
			+ " ORDER BY id DESC LIMIT 1", nativeQuery = true)
	public EntityChatWhatsappMaster returnSessionMessageIncidentDetails(String conversation_id_wac, String phoneNumber);


	@Query(value = "SELECT * FROM whatsappchatmaster_wac em WHERE em.conversation_id_wac = ?1 AND em.phone_number_wac = ?2 And"
			+ "  em.text_message_wac=?3 "
			+ " AND em.deleteflag_wac = 'N' "
			+ " ORDER BY id DESC LIMIT 1", nativeQuery = true)
	public EntityChatWhatsappMaster returnSessionMessageIncidentDetailsWithPassingMessage(String conversation_id_wac, 
			String phoneNumber,String message);


   //QUERY IMPORTANT TO RETURN CHATLIST FOR USER SCREEN WITHOUT DATE	
	@Query("SELECT em.textMessageWac,em.senderReceiverWac,em.createdatetimeEsm,em.statusStringWac FROM EntityChatWhatsappMaster em WHERE em.deleteflagWac='N' AND  em.conversationIdWac IS NOT NULL AND (em.waIdWac=?1 OR em.phoneNumberWac=?1) ORDER BY em.id ASC")
	public List<Object[]> findUsersChatsBy_Phone(String waIdWac);
	
}
