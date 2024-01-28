package net.intelliuno.repository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.intelliuno.entity.EntityIncidentMaster;


public interface RepositoryEntityIncidentMaster extends JpaRepository<EntityIncidentMaster, Long>{

	
	@Query("SELECT em FROM EntityIncidentMaster em WHERE em.typeidIm=?1 OR em.ticketidIm =?2 ")
	public EntityIncidentMaster returnIncidentDetailsForWati(Long n_LongTypeId,String m_strTypeid);

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
		
}
