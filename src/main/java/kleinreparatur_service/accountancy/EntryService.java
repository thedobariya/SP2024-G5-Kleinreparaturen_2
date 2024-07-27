package kleinreparatur_service.accountancy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service class for handling operations related to financial entries.
 */
@Service
public class EntryService {

	private final EntryRepository entryRepository;

	@Autowired
	public EntryService(EntryRepository entryRepository) {
		this.entryRepository = entryRepository;
	}

	/**
	 * Adds a new entry to the database.
	 * @param entry The entry to be added
	 * @return The saved entry
	 */
	public Entry addEntry(Entry entry) {
		return entryRepository.save(entry);
	}

	/**
	 * Retrieves all entries between two dates.
	 * @param start The start date
	 * @param end The end date
	 * @return A list of entries within the date range
	 */
	public List<Entry> getEntriesBetweenDates(LocalDate start, LocalDate end) {
		return entryRepository.findByDateBetween(start, end);
	}

	/**
	 * Calculates the total amount of all entries between two dates.
	 * @param start The start date
	 * @param end The end date
	 * @return The total amount
	 */
	public double getTotalAmountBetweenDates(LocalDate start, LocalDate end) {
		return getEntriesBetweenDates(start, end).stream()
			.mapToDouble(Entry::getAmount)
			.sum();
	}

	/**
	 * Groups entries by month for a given year.
	 * @param year The year for which to group entries
	 * @return A map of entries grouped by month
	 */
	public Map<YearMonth, List<Entry>> getEntriesGroupedByMonth(int year) {
		LocalDate startDate = LocalDate.of(year, 1, 1);
		LocalDate endDate = LocalDate.of(year, 12, 31);
		List<Entry> entries = getEntriesBetweenDates(startDate, endDate);

		return entries.stream()
			.collect(Collectors.groupingBy(entry -> YearMonth.from(entry.getDate())));
	}

	/**
	 * Retrieves all entries for a specific month.
	 * @param yearMonth The year and month for which to retrieve entries
	 * @return A list of entries for the specified month
	 */
	public List<Entry> getEntriesForMonth(YearMonth yearMonth) {
		LocalDate startDate = yearMonth.atDay(1);
		LocalDate endDate = yearMonth.atEndOfMonth();
		return getEntriesBetweenDates(startDate, endDate);
	}

	/**
	 * Calculates the total amount for a specific year.
	 * @param year The year for which to calculate the total
	 * @return The total amount for the year
	 */
	public double getTotalAmountForYear(int year) {
		LocalDate startDate = LocalDate.of(year, 1, 1);
		LocalDate endDate = LocalDate.of(year, 12, 31);
		return getTotalAmountBetweenDates(startDate, endDate);
	}

	/**
	 * Calculates the total amount for a specific month.
	 * @param yearMonth The year and month for which to calculate the total
	 * @return The total amount for the month
	 */
	public double getTotalAmountForMonth(YearMonth yearMonth) {
		LocalDate startDate = yearMonth.atDay(1);
		LocalDate endDate = yearMonth.atEndOfMonth();
		return getTotalAmountBetweenDates(startDate, endDate);
	}
}