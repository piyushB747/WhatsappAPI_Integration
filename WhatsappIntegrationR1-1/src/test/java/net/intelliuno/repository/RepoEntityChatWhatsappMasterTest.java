package net.intelliuno.repository;


import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import net.intelliuno.commons.CommonConstants;

import net.intelliuno.entity.EntityChatWhatsappMaster;

@SpringBootTest
class RepoEntityChatWhatsappMasterTest {

	@Autowired
	private RepoEntityChatWhatsappMaster repoEntityChatWhatsappMaster;
	
	
	/*
	 * IN THIS WE ARE TESTING NATIVE QUERY
	 
	@Query(value="SELECT em FROM whatsappchatmaster_wac em WHERE em.conversation_id_wac=?1  em.phone_number_wac=?2
	  AND em.deleteflagWac='N' order by id desc limit 1 ",nativeQuery=true)
	public EntityChatWhatsappMaster returnSessionMessageIncidentDetails(String conversation_id_wac,String phoneNumber); 
	
	 */
	@Test
	void testToCheckNativeQuery_returnSessionMessageIncidentDetails() {
		EntityChatWhatsappMaster e1=new EntityChatWhatsappMaster();
		//e1=repoEntityChatWhatsappMaster.returnSessionMessageIncidentDetails("64f05cc6581ced89457b68a1","7506214796","James Bond");
		e1=repoEntityChatWhatsappMaster.returnSessionMessageIncidentDetailsWithPassingMessage("64f05cc6581ced89457b68a1","7506214796","James Bond");
		
		System.out.println(" RepoEntityChatWhatsappMasterTest= "+e1);
	}

	
	@Test
	public void testTOFetchChatOnSingleViewIncidentPage() {
		List<EntityChatWhatsappMaster> l_objList=new ArrayList<EntityChatWhatsappMaster>();
		//List<DtoWhatsappChatting> l_objList2=new ArrayList<DtoWhatsappChatting>();
		
		l_objList=repoEntityChatWhatsappMaster.findUsersByIncidentIdAndPhone("30260", "7506214796");
		System.out.println("PIYUSH SINGH" +l_objList);

		for(int i=0;i<l_objList.size();i++) {
			EntityChatWhatsappMaster e1=l_objList.get(i);
			System.out.println(CommonConstants.red+" "+e1.getTextMessageWac()+" "+CommonConstants.reset);
		}
	}
	
}
