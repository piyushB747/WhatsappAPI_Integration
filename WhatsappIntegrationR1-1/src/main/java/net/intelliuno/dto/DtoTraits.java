package net.intelliuno.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class DtoTraits {
	private String name;
    private boolean whatsapp_opted_in;
    private String source_id;
    private String source_url;
}
