package net.intelliuno.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DtoRequestObject {
	
	public String m_strMessage;
	
	public String m_strPhoneNo;
	
	public String m_strIncidentId;
	
	public String m_strEngName;
	
	public String m_strTemplateName;
	
	public String m_strMessageType;
	
}
