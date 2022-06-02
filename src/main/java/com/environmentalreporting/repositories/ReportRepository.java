package com.environmentalreporting.repositories;

import com.environmentalreporting.models.Report;
import com.environmentalreporting.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Long> {
    Optional<List<Report>> findByUserId(long id);

    Optional<List<Report>> findByUser(User user);

}
