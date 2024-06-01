package kickstart.order;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {

	@GetMapping("/cart")
	public String showCart(Model model) {
		List<Service> services = new ArrayList<>();
//		services.add(
//			new Service("Service 1.1", 10.00));
//		services.add(new Service("Service 1.2", 20.00));
//		services.add(new Service("Service 1.3", 15.00));
//		services.add(new Service("Service 2.1", 10.00));
//		services.add(new Service("Service 2.2", 20.00));
//		services.add(new Service("Service 2.3", 15.00));
//		services.add(new Service("Service 3.1", 10.00));
//		services.add(new Service("Service 3.2", 20.00));
//		services.add(new Service("Service 3.3", 15.00));
//
//		double total = services.stream().mapToDouble(Service::getPrice).sum();

//		model.addAttribute("services", services);
//		model.addAttribute("total", String.format("$%.2f", total));

		return "cart";
	}

}
