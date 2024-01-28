package net.intelliuno.custrepo;





import java.util.List;

public interface CustomRepoEntityIncidentMaster {
	
	
    /**Main For List Of Select * from  Dash board You can use [Select * from]  **/
    public abstract List<Object[]>  returnListOfSelectStarFromConcept(String m_strQuery);

    /**Main For List Of Select * from  Dash board You can use [Select * from]  **/
    public abstract List<String>  returnListOfSelectOneColumnFromConcept(String m_strQuery);

    
	/**Main For Executive Dash board**/
	public abstract long getOpenCancelledCountForExecutiveWithParameterized(String paramQuery);
	
	public abstract int getOpenCancelledCountForExecutiveWithParameterizedNative(String paramQuery);
	
	/**Main For Engineer Attendance Dash board You can use [Select * from]  **/
    public abstract List<Object[]>  returnListOfEngineerAttendance(String m_strQuery);
    
	/**Main For List Of Pagination  Dash board You can use [Select * from]  **/
    public abstract List<Object[]>  returnListOfPageinationConcept(String m_strQuery);
	
   
	
  
    /**Main For Engineer Id Dash board**/
    public abstract List<Object[]> returnListOfEngineerIdForTree(String m_strQuery);
    

    
    /**ADDED FOR PASSWORD UPDATE**/
    public abstract void insertEngineerRoleNativeQuery(String userId,String roleId);
    
    /** DEVELOPMENT FOR SEARCH IMPLEMENTATION FOR TICKET ID WORKING PERFECTLY AUG15**/
    public abstract String getStringValue(String m_strQuery);
    
    /** DEVELOPMENT FOR SEARCH IMPLEMENTATION FOR TICKET ID WORKING PERFECTLY AUG15**/
    public List<String> getListStringValues(String m_strQuery);

}
