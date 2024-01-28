package net.intelliuno.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.intelliuno.commons.CommonConstants;
import net.intelliuno.custrepo.CustomRepoEntityIncidentMaster;
import net.intelliuno.dto.DtoMesssage;
import net.intelliuno.dto.DtoTemplate;
import net.intelliuno.dto.watti.DtoParameter;
import net.intelliuno.dto.watti.DtoTemplateRequest;

@Service
public class ServiceMessageBodyObject {

	@Autowired
	private CustomRepoEntityIncidentMaster customRepoEntityIncidentMaster;

	public DtoTemplateRequest returnListOfDtoMessageForEtaExpireWati(String m_strIncidentId, String m_strAssetId,
			String m_strTemplateName, String m_strEngContact, String m_strMessageType, String m_strEngineerName,
			String m_strAging, String m_strEnggID) {

		LocalDate obj_CurrentDate = LocalDate.now();
		LocalTime currentTime = LocalTime.now();
		List<DtoParameter> dtoParametersList = new ArrayList<>();

		if (m_strAging == null || m_strAging.equals("")) {
			m_strAging = obj_CurrentDate + " " + currentTime;
		}

		DtoParameter dtoParameter1 = new DtoParameter();
		DtoParameter dtoParameter2 = new DtoParameter();
		DtoParameter dtoParameter3 = new DtoParameter();
		DtoParameter dtoParameter4 = new DtoParameter();
		DtoParameter dtoParameter5 = new DtoParameter();
		DtoParameter dtoParameter6 = new DtoParameter();

		if (m_strMessageType.equals("ETA Update Required") || m_strMessageType.equals("ATA Update Required")) {

			dtoParameter1.setName("engg_name");
			dtoParameter1.setValue(m_strEngineerName);

			dtoParameter2.setName("ticket_id");
			dtoParameter2.setValue(m_strIncidentId);

			dtoParameter3.setName("asset_id");
			dtoParameter3.setValue(m_strAssetId);

			dtoParameter4.setName("incident_aging");
			dtoParameter4.setValue(m_strAging);

			dtoParameter5.setName("app_url");
			dtoParameter5.setValue(CommonConstants.OPEN_URL_FOR_WHATSAPP1);

			dtoParameter6.setName("cust_name");
			dtoParameter6.setValue(CommonConstants.CUSTOMER_NAME);
		} else if (m_strMessageType.equals("Follow Up Expired")) {

			String m_strFollowDate = "";
			String m_strFollowTime = "";

			/* DEVELOPMENT FOR FOLLOW UPDATE */
			try {
				List<Object[]> l1;
				String m_strQueryForFollowUpdate = "SELECT followupdate_irm,followuptime_irm from " + " "
						+ CommonConstants.db_Name + ".incremarkmst_irm where typeid_im_irm='" + m_strIncidentId
						+ "' order by typeid_irm desc limit 1  ";
				l1 = customRepoEntityIncidentMaster.returnListOfEngineerAttendance(m_strQueryForFollowUpdate);

				if (l1 != null && l1.isEmpty() == false) {
					for (Object[] m_strRmData : l1) {
						m_strFollowDate = net.intelliuno.commons.CommonUtils.nullToBlank(String.valueOf(m_strRmData[0]),
								false);
						m_strFollowTime = net.intelliuno.commons.CommonUtils.nullToBlank(String.valueOf(m_strRmData[1]),
								false);

					}

				} else {
					m_strFollowDate = " " + obj_CurrentDate;
					m_strFollowTime = " " + currentTime;
				}

			} catch (Exception ex) {
				ex.printStackTrace();
				m_strFollowDate = " " + obj_CurrentDate;
				m_strFollowTime = " " + currentTime;
			}

			if (m_strFollowDate == null || m_strFollowDate.equals("") || m_strFollowDate.equals("null")) {
				m_strFollowDate = " " + obj_CurrentDate;
			}

			if (m_strFollowTime == null || m_strFollowTime.equals("") || m_strFollowTime.equals("null")) {
				m_strFollowTime = " " + currentTime;
			}
			/* DEVELOPMENT FOR FOLLOW UPDATE ENDS */

			dtoParameter1.setName("engg_name");
			dtoParameter1.setValue(m_strEngineerName);

			dtoParameter2.setName("ticket_id");
			dtoParameter2.setValue(m_strIncidentId);

			dtoParameter3.setName("asset_id");
			dtoParameter3.setValue(m_strAssetId);

			dtoParameter4.setName("followup_date");
			dtoParameter4.setValue("" + m_strFollowDate);

			dtoParameter5.setName("followup_time");
			dtoParameter5.setValue(m_strFollowTime + "");

			dtoParameter6.setName("cust_name");
			dtoParameter6.setValue(CommonConstants.CUSTOMER_NAME);

		} else if (m_strMessageType.equals("WIP STATUS")) {
			dtoParameter1.setName("engg_name");
			dtoParameter1.setValue(m_strEngineerName);

			dtoParameter2.setName("ticket_id");
			dtoParameter2.setValue(m_strIncidentId);

			dtoParameter3.setName("asset_id");
			dtoParameter3.setValue(m_strAssetId);

			dtoParameter4.setName("cust_name");
			dtoParameter4.setValue(CommonConstants.CUSTOMER_NAME);

		} else if (m_strMessageType.equals("Call assignment to engineer")) {

			String l_strAssignedDateTime = "";
			dtoParameter1.setName("name");
			dtoParameter1.setValue(m_strEngineerName);

			dtoParameter2.setName("ticket_id");
			dtoParameter2.setValue(m_strIncidentId);

			dtoParameter3.setName("asset_id");
			dtoParameter3.setValue(m_strAssetId);
			try {
				String l_strQueryForAssignedDateTime = "select concat(COALESCE(changedate_ilm,''),' ',COALESCE(changetime_ilm,''))  as AssignedDateTime"
						+ " from incidentlogmst_ilm where incidentid_ilm='" + m_strIncidentId + "'"
						+ " and column_ilm='engineer_engm_im' and newvalue_ilm='" + m_strEnggID + "'"
						+ " order by typeid_ilm desc limit 1";
				l_strAssignedDateTime=customRepoEntityIncidentMaster.getStringValue(l_strQueryForAssignedDateTime);
				
			} catch (Exception e) {
			}
			dtoParameter4.setName("ticket_time");
			dtoParameter4.setValue(l_strAssignedDateTime);
		}

		if (m_strMessageType.equals("WIP STATUS") == false && m_strMessageType.equals("Call assignment to engineer")==false) {
			dtoParametersList.add(dtoParameter6);
			dtoParametersList.add(dtoParameter5);
		}
		dtoParametersList.add(dtoParameter4);
		dtoParametersList.add(dtoParameter3);
		dtoParametersList.add(dtoParameter2);
		dtoParametersList.add(dtoParameter1);

		DtoTemplateRequest dtoTemp = new DtoTemplateRequest();
		dtoTemp.setBroadcast_name(m_strMessageType);
		dtoTemp.setTemplate_name(m_strTemplateName);
		dtoTemp.setParameters(dtoParametersList);

		return dtoTemp;
	}

	public DtoTemplateRequest returnListOfDtoMessageForRMData(String m_strIncidentId, String m_strAssetId,
			String m_strTemplateName, String m_strEngContact, String m_strMessageType, String m_strEngineerName,
			String m_strAging, String m_strfollowupdateIrm, String m_strFollowuptimeIrm) {

		List<DtoParameter> dtoParametersList = new ArrayList<>();

		DtoParameter dtoParameter1 = new DtoParameter();
		DtoParameter dtoParameter2 = new DtoParameter();

		if (m_strMessageType.equals("RM AVAILABILITY") || m_strMessageType.equals("RM AVAILABILITY")) {

			dtoParameter1.setName("name");
			dtoParameter1.setValue(m_strEngineerName);

			dtoParameter2.setName("cust_name");
			dtoParameter2.setValue(CommonConstants.CUSTOMER_NAME + "");

		}
		dtoParametersList.add(dtoParameter2);
		dtoParametersList.add(dtoParameter1);

		DtoTemplateRequest dtoTemp = new DtoTemplateRequest();
		dtoTemp.setBroadcast_name(m_strMessageType);
		dtoTemp.setTemplate_name(m_strTemplateName);
		dtoTemp.setParameters(dtoParametersList);

		return dtoTemp;
	}

	/* USED FOR INTERAKIT */
	public DtoMesssage returnListOfDtoMessageForEtaExpire(String m_strIncidentType, String m_strAssetId,
			String m_strTemplateName, String m_strPhoneNumber, String m_strSiteId) {
		DtoMesssage requestObject = new DtoMesssage();

		DtoTemplate template = new DtoTemplate();
		template.setName(m_strTemplateName);
		template.setLanguageCode("en");
		template.getBodyValues().add(m_strIncidentType);

		requestObject.setCountryCode("+91");
		requestObject.setPhoneNumber(m_strPhoneNumber);
		requestObject.setCallbackData("Generation From Piyush");
		requestObject.setType("Template");
		requestObject.setTemplate(template);

		return requestObject;
	}
}
