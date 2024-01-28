package net.intelliuno.dto;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
public class DtoTemplate {

	private String name;
    private String languageCode;
    private List<String> bodyValues = new ArrayList<>();

}
