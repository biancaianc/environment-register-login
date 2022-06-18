package com.environmentalreporting.security.services;

import com.environmentalreporting.exceptions.AlreadyReportedInThatArea;
import com.environmentalreporting.models.Report;
import com.environmentalreporting.models.User;
import com.environmentalreporting.payload.requests.ReportRequest;
import com.environmentalreporting.payload.responses.ReportResponse;
import com.environmentalreporting.repositories.ReportRepository;
import com.environmentalreporting.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class ReportService {
    @Autowired
    ReportRepository reportRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReportService reportService;

    @Autowired
    ImageService imageService;

    public boolean validate(Report report) throws AlreadyReportedInThatArea {
        Optional<List<Report>> byUserId = reportRepository.findByUserId(report.getUser().getId());
        if (byUserId.isPresent() && !byUserId.isEmpty()) {
            List<Report> reportsFromTheSameDay = getReportsFromTheSameDay(report, byUserId.get());
            if(!validateLatAndLong(report, reportsFromTheSameDay).isEmpty()) {
                throw new AlreadyReportedInThatArea(report.getUser().getUsername()+ " already reported in that area");
            }
        }
        return true;
    }

    private List<Report> validateLatAndLong(Report report, List<Report> reportsFromTheSameDay) throws AlreadyReportedInThatArea {
        reportsFromTheSameDay = reportsFromTheSameDay.stream().filter
                (x -> Math.abs(x.getLatitude() - report.getLatitude()) < 0.0001 ||
                        Math.abs(x.getLongitude() - report.getLongitude()) < 0.0001).collect(Collectors.toList());
        return reportsFromTheSameDay;
    }

    private List<Report> getReportsFromTheSameDay(Report report, List<Report> reportsFromUser) {
        reportsFromUser = reportsFromUser.stream().filter(x -> TimeUnit.DAYS.convert(Math.abs(report.getDate().getTime() -
                x.getDate().getTime()), TimeUnit.MILLISECONDS) == 0).collect(Collectors.toList());
        return reportsFromUser;
    }

    public void deleteReport(long id) {
        Optional<Report> byId = reportRepository.findById(id);
        if(byId.isPresent()){
            Report report = byId.get();
            User user = report.getUser();
            Set<Report> reports = user.getReports();
            reports.remove(report);
            reportRepository.deleteById(id);
        }
    }

    public Report createReport(ReportRequest report) throws AlreadyReportedInThatArea {
        Optional<User> user = userRepository.findByUsername(report.getUser());
        if (user.isPresent()) {
            User u = user.get();
            userRepository.save(u);
            Report entity = new Report(report.getName(), report.getCity(), report.getRegion(), report.getLatitude(), report.getLongitude(), user.get(), report.isApproved(), report.getDescription(), report.getType(), report.getImagePath(), 0);
            reportService.validate(entity);
            reportRepository.save(entity);
            return entity;
        }
        else throw new IllegalArgumentException("Invalid user");
    }


    public Report getReport(long id) {
        Optional<Report> byId = reportRepository.findById(id);
        if(byId.isPresent()){
            return byId.get();
        }
        throw  new IllegalArgumentException("Report not found");
    }

    public List<ReportResponse> getReports() {
        List<Report> reports = new ArrayList<>();
        List<ReportResponse> reportResponses = new ArrayList<>();
        reportRepository.findAll().forEach(reports::add);
        reports.forEach(x -> reportResponses.add(new ReportResponse(x.getId(), x.getName(), x.getDate(), x.getCity(), x.getRegion(),
                x.getLatitude(), x.getLongitude(), x.getUser(), x.isApproved(), x.getDescription(), x.getType().name(), x.getImagePath(), x.getReactions())));
        return reportResponses;
    }

    public List<ReportResponse>
    getReportsFromUser(Long id) {
        List<ReportResponse> reportResponses = new ArrayList<>();
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            Optional<List<Report>> reporterData = reportRepository.findByUser(user.get());
            if (reporterData.isPresent()) {
                reporterData.get().forEach(x -> reportResponses.add(new ReportResponse(x.getId(), x.getName(), x.getDate(), x.getCity(), x.getRegion(),
                        x.getLatitude(), x.getLongitude(), x.getUser(), x.isApproved(), x.getDescription(), x.getType().name(), x.getImagePath(), x.getReactions())));
            }
        }
        return reportResponses;
    }

    public Report updateReport(Report report) {
       return reportRepository.save(report);
    }
}
