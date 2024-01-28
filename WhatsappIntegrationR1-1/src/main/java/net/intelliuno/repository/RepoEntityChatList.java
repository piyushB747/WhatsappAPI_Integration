package net.intelliuno.repository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import net.intelliuno.entity.EntityChatList;


public interface RepoEntityChatList extends JpaRepository<EntityChatList, Long>{


	
	@Transactional
	@Modifying
	@Query("DELETE FROM EntityChatList e WHERE e.phoneNumberWcl =?1 ")
	void deleteByPhoneNumberWcl(String phoneNumberWcl);
	
	@Query("SELECT ei FROM EntityChatList ei where ei.deleteflagWcl='N' order by ei.idWcl desc ")
	public abstract List<EntityChatList> returnListOfTicketCreatedByMobile();
     
	
}
