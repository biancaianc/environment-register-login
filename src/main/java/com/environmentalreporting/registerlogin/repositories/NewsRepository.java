package com.environmentalreporting.registerlogin.repositories;

import com.environmentalreporting.registerlogin.models.News;
import com.environmentalreporting.registerlogin.models.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {
}
