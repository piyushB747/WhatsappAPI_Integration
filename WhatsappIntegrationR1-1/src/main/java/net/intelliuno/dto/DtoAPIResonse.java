package net.intelliuno.dto;

import java.util.List;
import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoAPIResonse {

	private Map<String, List<String>> headers;
	private String body;
	private String statusCode;
	private int statusCodeValue;
	private boolean result; 
	private String message; 
	private String id; 

	
}
