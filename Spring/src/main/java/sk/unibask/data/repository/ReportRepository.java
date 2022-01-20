package sk.unibask.data.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sk.unibask.data.model.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {

}
