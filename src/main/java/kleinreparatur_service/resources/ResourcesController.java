package kleinreparatur_service.resources;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/resources")
public class ResourcesController {

	@GetMapping("")
	@PreAuthorize("isAuthenticated()")
	public String resources() {
		return "resources";
	}
}
