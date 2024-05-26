package kickstart.managment;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagmentController {

	@GetMapping("management.html")
	public String management() { return "management"; }

	@GetMapping("resources.html")
	public String resources() { return "resources"; }

	@GetMapping("finance.html")
	public String finance() { return "finance"; }

	@GetMapping("depot.html")
	public String depot() { return "depot"; }

	@GetMapping("cart.html")
	public String cart() { return "cart"; }

	@GetMapping("servicecatalog.html")
	public String servicecatalog() { return "servicecatalog";}
}