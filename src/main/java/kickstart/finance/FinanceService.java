package kickstart.finance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinanceService {

	private final FinanceRepository financeRepository;

	@Autowired
	public FinanceService(FinanceRepository financeRepository) {
		this.financeRepository = financeRepository;
	}

	public Finance createDummyFinance() {
		Finance finance = new Finance();
		finance.setYearMonth(202101);
		finance.setValue(1000);
		return financeRepository.save(finance);
	}

	public List<Finance> getAllFinances() {
		return financeRepository.findAll();
	}
}