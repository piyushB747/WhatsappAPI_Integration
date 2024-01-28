package net.intelliuno.custrepo;



import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.Query;

@Repository
@Transactional(readOnly = true)
public class ImplCustomRepoEntityIncidentMaster implements CustomRepoEntityIncidentMaster {

	@PersistenceContext
	EntityManager entityManager;
	
	/**YOU CAN USE THIS FOR SELECT * FROM**/
	@Override
	public List<Object[]> returnListOfSelectStarFromConcept(String m_strQuery) {
		@SuppressWarnings("unchecked")
        List<Object[]> resultList = entityManager.createNativeQuery(m_strQuery)
                .getResultList();
        return resultList;
	}

	
	@Override
	public List<String> returnListOfSelectOneColumnFromConcept(String m_strQuery) {
		@SuppressWarnings("unchecked")
		List<String> resultList = entityManager.createNativeQuery(m_strQuery)
		        .getResultList();
		return resultList;
	}


	/**Using For Executive Dash board Engineer Ticket Count **/
	@Override
	public long getOpenCancelledCountForExecutiveWithParameterized(String paramQuery) {
		TypedQuery<Long> query = entityManager.createQuery(paramQuery, Long.class);
		return query.getSingleResult();

	}
	

	
	/**Main For Engineer Attendance Dash board**/
	@Override
	public List<Object[]>  returnListOfEngineerAttendance(String m_strQuery) {
        
        @SuppressWarnings("unchecked")
        List<Object[]> resultList = entityManager.createNativeQuery(m_strQuery)
                .getResultList();
        return resultList;
    }



	
	/**Main Return Imp Tree Function**/
	@Override
	public List<Object[]> returnListOfEngineerIdForTree(String m_strQuery) {		
		
		@SuppressWarnings("unchecked")
        List<Object[]> resultList = entityManager.createNativeQuery(m_strQuery).getResultList();
        
        return resultList;
	}


	
	/*Return String Only*/
	public String returnStringFromQuery(String m_strQuery) {
	    TypedQuery<String> query = entityManager.createQuery(m_strQuery, String.class);
	    return query.getSingleResult();
	}

	
	
	
	public Double returnAvgTicketCostForEngineer(String m_strQuery) {
	    Query query = entityManager.createNativeQuery(m_strQuery);
	    @SuppressWarnings("unchecked")
		List<Double> resultList = query.getResultList();
	    return resultList.isEmpty() ? null : resultList.get(0);
	}

	
	public String returnAvgTicketCostForEngineerString(String m_strQuery) {
	    Query query = entityManager.createNativeQuery(m_strQuery);
	    @SuppressWarnings("unchecked")
		List<String> resultList = query.getResultList();
	    return resultList.isEmpty() ? null : resultList.get(0);
	}
	
	
	
	


	
	@Override
	public int getOpenCancelledCountForExecutiveWithParameterizedNative(String paramQuery) {
		Query query = entityManager.createNativeQuery(paramQuery);
	    return ((Number) query.getSingleResult()).intValue();
	}
	
	
	
    /*RETURN LIST OF PAGEINATION  */
	@Override
	public List<Object[]> returnListOfPageinationConcept(String m_strQuery) {
		    @SuppressWarnings("unchecked")
	        List<Object[]> resultList = entityManager.createNativeQuery(m_strQuery)
	                .getResultList();
	        return resultList;
	}


	
	
	/**ADDED FOR PASSWORD UPDATE ON 7AUG BY PIYUSHRAJ**/
	@Override
	@Transactional
	public void insertEngineerRoleNativeQuery(String userId, String roleId) {
		 String nativeQuery = "INSERT INTO engineer_roles_er (user_id_er, role_id_er) VALUES (?, ?)";
	        entityManager.createNativeQuery(nativeQuery)
	            .setParameter(1, userId) // user_id_er
	            .setParameter(2, roleId) // role_id_er
	            .executeUpdate();
	}

	/** DEVELOPMENT FOR SEARCH IMPLEMENTATION FOR TICKET ID WORKING PERFECTLY 15AUGUST**/
	@Override
	public String getStringValue(String m_strQuery) {

	    Query query = entityManager.createNativeQuery(m_strQuery);
	    
	    Object result = query.getSingleResult();
	    
	    if (result != null) {
	        return result.toString();
	    } else {
	        return "No Result"; // or an appropriate default value
	    }
	}
	
	/** DEVELOPMENT FOR SEARCH IMPLEMENTATION FOR TICKET ID WORKING PERFECTLY **/
	@Override
	public List<String> getListStringValues(String m_strQuery) {
		  Query query = entityManager.createNativeQuery(m_strQuery);

		    @SuppressWarnings("unchecked")
			List<Object> results = query.getResultList();
		    List<String> stringResults = new ArrayList<>();

		    for (Object result : results) {
		        if (result != null) {
		            stringResults.add(result.toString());
		        } else {
		            stringResults.add("No Result"); // or an appropriate default value
		        }
		    }

		    return stringResults;
	}








}
