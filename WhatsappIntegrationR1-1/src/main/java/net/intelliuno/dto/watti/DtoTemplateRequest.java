package net.intelliuno.dto.watti;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DtoTemplateRequest {
	private String template_name;
	private String broadcast_name;
	private List<DtoParameter> parameters;

}
