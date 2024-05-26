package kickstart.finance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
public class FinanceController {

	private final FinanceService financeService;

	@Autowired
	public FinanceController(FinanceService financeService) {
		this.financeService = financeService;
	}

	@GetMapping("/finance")
	public String getFinanceData(Model model) {
		List<Finance> finances = financeService.getAllFinances();
		model.addAttribute("finances", finances);
		return "finance";
	}

	@PostMapping("/finance")
	public String getFinanceDataForSpecificMonth(@RequestParam("year") int year, @RequestParam("month") int month, Model model) {
		List<Finance> finances = financeService.getAllFinances();
		model.addAttribute("finances", finances);
		return "finance";
	}

	/**
	 * Get the finance data for a specific year and month
	 * @param year
	 * @param month
	 * @return
	 */
	//@PostMapping("/finance")
	//public ModelAndView getFinanceData(@RequestParam("year") int year, @RequestParam("month") int month) {
		// Placeholder for the actual data
	//}
}