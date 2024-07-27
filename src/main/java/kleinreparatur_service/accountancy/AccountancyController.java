package kleinreparatur_service.accountancy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Controller for handling accountancy-related requests.
 * Only accessible to users with MANAGEMENTLEAD role.
 */
@Controller
@RequestMapping("/accountancy")
@PreAuthorize("hasRole('MANAGEMENTLEAD')")
public class AccountancyController {

	private final EntryService entryService;
	private static final Logger logger = LoggerFactory.getLogger(AccountancyController.class);

	@Autowired
	public AccountancyController(EntryService entryService) {
		this.entryService = entryService;
	}

	/**
	 * Handles GET requests for accountancy data.
	 *
	 * @param period The period for which to fetch data (yearly or monthly)
	 * @param yearMonth The specific month and year for monthly view
	 * @param year The specific year for yearly view
	 * @param model The Spring MVC Model
	 * @return The name of the view to render
	 */
	@GetMapping
	public String accountancy(
		@RequestParam(required = false) String period,
		@RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM") YearMonth yearMonth,
		@RequestParam(required = false) Integer year,
		Model model) {

		logger.info("Received request - period: {}, yearMonth: {}, year: {}", period, yearMonth, year);

		LocalDate now = LocalDate.now();
		YearMonth currentYearMonth = YearMonth.from(now);

		// If no period is specified, show the initial view with buttons
		if (period == null) {
			model.addAttribute("showButtons", true);
			return "accountancy";
		}

		try {
			if ("yearly".equals(period)) {
				// Handle yearly view
				year = (year != null) ? year : now.getYear();
				year = Math.max(2020, Math.min(year, now.getYear()));
				Map<YearMonth, List<Entry>> entriesByMonth = entryService.getEntriesGroupedByMonth(year);
				double totalAmount = entryService.getTotalAmountForYear(year);
				model.addAttribute("entriesByMonth", entriesByMonth);
				model.addAttribute("totalAmount", totalAmount);
				model.addAttribute("year", year);
			} else {
				// Handle monthly view
				if (yearMonth == null || yearMonth.isBefore(YearMonth.of(2020, 1)) || yearMonth.isAfter(currentYearMonth)) {
					yearMonth = currentYearMonth;
				}
				List<Entry> entries = entryService.getEntriesForMonth(yearMonth);
				double totalAmount = entryService.getTotalAmountForMonth(yearMonth);
				model.addAttribute("entries", entries);
				model.addAttribute("totalAmount", totalAmount);
				model.addAttribute("yearMonth", yearMonth);
			}

			// Add common attributes
			model.addAttribute("viewPeriod", period);
			model.addAttribute("currentYear", now.getYear());
			model.addAttribute("hasEntries", true);

			return "accountancy";

		} catch (Exception e) {
			// Log the error and add an error message to the model
			logger.error("Error processing accountancy request", e);
			model.addAttribute("error", "An error occurred while processing your request. Please try again.");
			return "accountancy";
		}
	}
}