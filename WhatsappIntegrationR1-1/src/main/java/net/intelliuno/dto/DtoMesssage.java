package net.intelliuno.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DtoMesssage {

    private String countryCode;
    private String phoneNumber;
    private String callbackData;
    private String type;
    private DtoTemplate template;
    private boolean is_template_message; // Correct field name

	
	
}

