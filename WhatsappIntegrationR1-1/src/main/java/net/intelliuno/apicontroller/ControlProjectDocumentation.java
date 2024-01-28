package net.intelliuno.apicontroller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/whatsappdocumentation")
public class ControlProjectDocumentation {

	@GetMapping
	public String whatsappDocumentation() {
		return "JavaDocumentation";
	}
	
}
