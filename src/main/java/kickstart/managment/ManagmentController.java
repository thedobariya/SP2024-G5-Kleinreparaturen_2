package kickstart.managment;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ManagmentController {

	@GetMapping("managment.html")
	public String managment() { return "managment"; }

	@GetMapping("ressources.html")
	public String ressources() { return "ressources"; }

	@GetMapping("finance.html")
	public String finance() { return "finance"; }

	@GetMapping("depot.html")
	public String depot() { return "depot"; }

	@GetMapping("cart.html")
	public String cart() { return "cart"; }
}