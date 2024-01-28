package net.intelliuno.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.intelliuno.dto.message.DtoMessage;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoData {

	 private DtoCustomers customer;
	 private DtoMessage message;
}
