package net.intelliuno.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class DtoCustomers {
	   private String id;
	    private String channel_phone_number;
	    private String phone_number;
	    private String country_code;
	    private DtoTraits traits;

}
