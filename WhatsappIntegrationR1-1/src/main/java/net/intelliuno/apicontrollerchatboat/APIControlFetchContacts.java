package net.intelliuno.apicontrollerchatboat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.intelliuno.dto.DtoEntityEngineerMaster;
import net.intelliuno.service.ServiceEntityEngineerMaster;

@RestController
@RequestMapping(value="/essential")
@CrossOrigin(origins="*")
public class APIControlFetchContacts {

	
	@Autowired
	private ServiceEntityEngineerMaster serviceEngineerMaster;
	
	
	@GetMapping("/contacts")
	public ResponseEntity<?> sendChatsData(@RequestParam(name="m_strIncidentId",required = false)String m_strIncidentId,
			@RequestParam(name="phoneNumber",required = false)String m_strPhoneNumber){
		
		
		
			List<DtoEntityEngineerMaster> l1=serviceEngineerMaster.returnContactNameAndNumberForChattingScreen();
			
				return ResponseEntity.status(HttpStatus.OK).header("custom-header", "Web Send")
						  .contentType(MediaType.APPLICATION_JSON).body(l1);
				
		
		
		//return ResponseEntity.status(HttpStatus.BAD_REQUEST).header("custom-header", "Web Send")
//			//	  .contentType(MediaType.APPLICATION_JSON).body("");
	}
	
}
