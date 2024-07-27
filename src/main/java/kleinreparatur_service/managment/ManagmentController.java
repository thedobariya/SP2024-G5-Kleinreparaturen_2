package kleinreparatur_service.managment;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/management")
public class ManagmentController {

	@GetMapping()
	public String management() {
		return "management";
	}
}