package sk.unibask.report;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import sk.unibask.authentication.AuthenticationService;
import sk.unibask.data.model.Account;
import sk.unibask.data.model.Entry;
import sk.unibask.data.model.Report;
import sk.unibask.data.repository.EntryRepository;
import sk.unibask.data.repository.ReportRepository;

@Service
public class ReportService {
    private final AuthenticationService authenticationService;
    private final EntryRepository entryRepository;
    private final ReportRepository reportRepository;

    public ReportService(AuthenticationService authenticationService, EntryRepository entryRepository, ReportRepository reportRepository) {
        this.authenticationService = authenticationService;
        this.entryRepository = entryRepository;
        this.reportRepository = reportRepository;
    }

    @Transactional
    public void report(Long entryId, String reportReason) {
        Account loggedAccount = authenticationService.getLoggedAccount();
        Entry entry = entryRepository.findById(entryId).orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Záznam neexistuje."));

        var report = new Report();
        report.setReportReason(reportReason);
        report.setEntry(entry);
        report.setReportReason(reportReason);
        report.setReporter(loggedAccount);
        report.setEntryText(entry.getEntryText());
        reportRepository.save(report);
    }
}
