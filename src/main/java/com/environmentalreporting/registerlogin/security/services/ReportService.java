package com.environmentalreporting.registerlogin.security.services;

import com.environmentalreporting.registerlogin.exceptions.AlreadyReportedInThatArea;
import com.environmentalreporting.registerlogin.models.Report;
import com.environmentalreporting.registerlogin.models.User;
import com.environmentalreporting.registerlogin.payload.requests.ReportRequest;
import com.environmentalreporting.registerlogin.payload.responses.ReportResponse;
import com.environmentalreporting.registerlogin.repositories.ReportRepository;
import com.environmentalreporting.registerlogin.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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
        if (byUserId.isPresent()) {
            validateLatAndLong(report, byUserId.get());
            //validateDate(report, byUserId.get());
            return true;
        }
        return false;
    }

//    //validate if a user made > 5 reports in a day
//    private void validateDate(Report report, List<Report> reportsFromUser) {
//        Calendar calendarReported = Calendar.getInstance();
//        calendarReported.setTime(report.getDate());
//        reportsFromUser.stream().forEach(x->{
//            Calendar calendar = Calendar.getInstance();
//            calendar.setTime(x.getDate());
//            if(Calendar.getInstance().setTime(x.getDate()).get(Calendar.DAY_OF_WEEK) == calendarReported.get(Calendar.DAY_OF_WEEK))
//
//        } );
//    }

    private void validateLatAndLong(Report report, List<Report> reportsFromUser) throws AlreadyReportedInThatArea {
        List<Report> collect = reportsFromUser.stream().filter(x -> Math.abs(x.getLatitude() - report.getLatitude()) < 0.0001 || Math.abs(x.getLongitude() - report.getLongitude()) < 0.0001).collect(Collectors.toList());
        if(!collect.isEmpty())
             throw new AlreadyReportedInThatArea(report.getUser().getUsername()+ " already reported in that area");
    }

    public void approveReport(Report report) {
        report.setApproved(true);
        reportRepository.save(report);
    }

    public void deleteReport(long id) {
        reportRepository.deleteById(id);
    }

    public Report createReport(ReportRequest report) throws Exception {
        Optional<User> user = userRepository.findByUsername(report.getUser());
        if (user.isPresent()) {
            User u = user.get();
            u.setPoints(u.getPoints() + 3);
            userRepository.save(u);
            Report entity = new Report(report.getName(), report.getCity(), report.getRegion(), report.getLatitude(), report.getLongitude(), user.get(), report.isApproved(), report.getDescription(), report.getType(), report.getImageName(), 0);
            reportService.validate(entity);
            reportRepository.save(entity);
            return entity;
        }
        else throw new Exception("Invalid user");
    }

    public Report updateReactions(Long id) throws Exception {
        Optional<Report> byId = reportRepository.findById(id);
        if (byId.isPresent()) {
            Report report = byId.get();
            if(report.getReactions() != null)
                 report.setReactions(report.getReactions() + 1);
            else
                report.setReactions(1);

            Report entity = new Report(report.getName(), report.getCity(), report.getRegion(), report.getLatitude(), report.getLongitude(), report.getUser(), report.isApproved(), report.getDescription(), report.getType().name(), report.getImagePath(), report.getReactions());
            reportRepository.save(entity);
            return entity;
        }
        throw new Exception("Error at update reactions");
    }

    public Report getReport(long id) throws Exception {
        Optional<Report> byId = reportRepository.findById(id);
        if(byId.isPresent()){
            return byId.get();
        }
        throw  new Exception("Report not found");
    }
}
