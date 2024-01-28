package net.intelliuno.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.intelliuno.entity.EntityIncidentMaster;
import net.intelliuno.repository.RepositoryEntityIncidentMaster;
import net.intelliuno.service.ServiceEntityIncidentMaster;

@Service
public class ServiceImplEntityIncidentMaster implements ServiceEntityIncidentMaster{

	@Autowired
	private RepositoryEntityIncidentMaster repoEntityIncidentMaster;
	
	@Override
	public EntityIncidentMaster returnIncidentDetailsList(String m_strIncidentId) {
		m_strIncidentId = m_strIncidentId.replaceAll("\\s", "");

		
		Long n_LongTypeId=0L;
		try {
			n_LongTypeId=Long.valueOf(m_strIncidentId);	
		}catch(Exception ex) { ex.printStackTrace(); n_LongTypeId=0L; }
		
		EntityIncidentMaster e1=repoEntityIncidentMaster.returnIncidentDetailsForWati(n_LongTypeId,m_strIncidentId);
		if(e1!=null) {
			return e1;
		}
		return null;
	}

}
