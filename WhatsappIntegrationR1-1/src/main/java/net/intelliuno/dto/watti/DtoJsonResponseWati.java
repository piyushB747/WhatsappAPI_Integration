package net.intelliuno.dto.watti;

import java.util.List;
import lombok.Data;
import net.intelliuno.dto.DtoWhatsappChatting;

@Data
public class DtoJsonResponseWati {

	//public List<DtoTableData> tableData;
	
	 public String statusOfTable="No";
	 public String statusOfRM;
	 public String rmNumber;
     public String rmId;
	 public List<DtoWhatsappChatting> tableData;	
}
