package net.intelliuno.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import net.intelliuno.entity.EntityServiceRunningTimer;

public interface RepoEntityServiceRunningTimer extends JpaRepository<EntityServiceRunningTimer, Long>{

	
	@Query("SELECT em.serviceTimerSr FROM EntityServiceRunningTimer em WHERE em.deleteFlagSr = ?1 And em.serviceNameSr=?2 ")
	public abstract String findByServiceNameSr(String deleteFlagSr,String serviceNameSr);
	
	@Query(value=" select service_timer_sr from servicerunner_sr where delete_flag_sr=?1 And service_name_sr=?2  order by id_sr desc limit 1 ",nativeQuery = true)
	public abstract String findByServiceNameSrNative(String deleteFlagSr,String serviceNameSr);
	
	
	
}
