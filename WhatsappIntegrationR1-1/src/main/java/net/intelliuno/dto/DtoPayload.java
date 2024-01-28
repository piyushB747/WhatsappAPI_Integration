package net.intelliuno.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DtoPayload {
	   private String version;
	    private String timestamp;
	    private String type;
	    private DtoData data;

}
