package net.intelliuno.serviceimpl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import net.intelliuno.commons.CommonConstants;
import net.intelliuno.custrepo.CustomRepoEntityIncidentMaster;
import net.intelliuno.dto.DtoEntityEngineerMaster;
import net.intelliuno.service.ServiceEntityEngineerMaster;
import java.util.ArrayList;

@Service
public class ServiceImplEntityEngineerMaster implements ServiceEntityEngineerMaster{

	@Autowired
	private CustomRepoEntityIncidentMaster repoEntityIncidentMaster;
	
	@Override
	public List<DtoEntityEngineerMaster> returnContactNameAndNumberForChattingScreen() {
		List<DtoEntityEngineerMaster> l_objNewDtoEntityEngineerMst=new ArrayList<DtoEntityEngineerMaster>();
		
		String m_strQuery="";
		try {
		//	m_strQuery="Select typeid_em,concat(fname_em,' ',lname_em) as name,contact_em from "+CommonConstants.db_Name+".engineermst_em"
		//			+ "  where deleteflag_em='N'  and resignedflag_em='N' and contact_em!='' ";
			
			m_strQuery="select phone_number_wac,wa_id_wac from "+CommonConstants.db_Name+".whatsappchatmaster_wac "
					+ " where conversation_id_wac is NOT NULL and  deleteflag_wac='N' order by id desc";	
			
			System.out.println(m_strQuery);
			List<Object[]> l1;
			
			l1=repoEntityIncidentMaster.returnListOfSelectStarFromConcept(m_strQuery);
			List<String> l_ObjPhoneNumber=new ArrayList<String>();
			
			if(l1!=null &&l1.isEmpty()==false) {
				try {
					for(Object[] m_strRmData : l1) {
						
						  String waId="";String m_strPhoneNumber="";
						  
						  m_strPhoneNumber=String.valueOf(m_strRmData[0]).trim();
						  waId=String.valueOf(m_strRmData[1]).trim();
						  
                           if(m_strPhoneNumber!=null && m_strPhoneNumber.equals("")==false && m_strPhoneNumber.equals("null")==false) {
                               if (!l_ObjPhoneNumber.contains(m_strPhoneNumber)) {
                            	   l_ObjPhoneNumber.add(m_strPhoneNumber);
                               }
                           }else if(waId!=null && waId.equals("")==false && waId.equals("null")==false) {
                        	   if (!l_ObjPhoneNumber.contains(waId)) {
                            	   l_ObjPhoneNumber.add(waId);
                               }
                           }
						  
                           
					}
				}catch(Exception ex) { ex.printStackTrace(); }
			}
			
			l_objNewDtoEntityEngineerMst=returnContactNameAndNumberForChattingScreenToUser(l_ObjPhoneNumber);
		}catch(Exception ex) {  ex.printStackTrace(); }
		
		return l_objNewDtoEntityEngineerMst;
	}

	
	public List<DtoEntityEngineerMaster> returnContactNameAndNumberForChattingScreenToUser(List<String> l_ObjPhoneNumber) {
		List<DtoEntityEngineerMaster> l_objNewDtoEntityEngineerMst=new ArrayList<DtoEntityEngineerMaster>();
		String m_strQuery=" ";
		
		
		for(int i=0;i<l_ObjPhoneNumber.size();i++) {
			m_strQuery="Select typeid_em,concat(fname_em,' ',lname_em) as name,contact_em from "+CommonConstants.db_Name+".engineermst_em"
								+ "  where deleteflag_em='N'  and resignedflag_em='N' and contact_em='"+l_ObjPhoneNumber.get(i)+"' order"
										+ " by typeid_em desc limit 1";
		
			System.out.println("ROCK MADE ME TO THAT "+m_strQuery);
			List<Object[]> l1=repoEntityIncidentMaster.returnListOfSelectStarFromConcept(m_strQuery);
			if(l1!=null && l1.isEmpty()==false) {
				for(Object[] m_strRmData : l1) {
					
					  String typeidEm="";String nameEm="";
					  DtoEntityEngineerMaster dtoE1=new DtoEntityEngineerMaster();
					  typeidEm=String.valueOf(m_strRmData[0]).trim();
					  nameEm=String.valueOf(m_strRmData[1]).trim()+" ("+String.valueOf(m_strRmData[2]).trim()+")";

					  dtoE1.setPersonName(StringUtils.capitalize(String.valueOf(m_strRmData[1]).trim()));
					  dtoE1.setNameEm(nameEm);
					  dtoE1.setPhoneNumberEm(String.valueOf(m_strRmData[2]).trim());
					  dtoE1.setTypeidEm(typeidEm);
					  

					  
					  l_objNewDtoEntityEngineerMst.add(dtoE1);
					  dtoE1=null;
					  
				}
			}
		}
		return l_objNewDtoEntityEngineerMst;
	}
	
	
	
}
