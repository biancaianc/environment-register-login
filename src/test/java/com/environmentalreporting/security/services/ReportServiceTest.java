package com.environmentalreporting.security.services;

import com.environmentalreporting.exceptions.AlreadyReportedInThatArea;
import com.environmentalreporting.models.EReport;
import com.environmentalreporting.models.Report;
import com.environmentalreporting.models.User;
import com.environmentalreporting.payload.requests.ReportRequest;
import com.environmentalreporting.payload.responses.ReportResponse;
import com.environmentalreporting.repositories.ReportRepository;
import com.environmentalreporting.repositories.UserRepository;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@SpringBootTest
public class ReportServiceTest {

    @MockBean
    ReportRepository reportRepository;

    @MockBean
    UserRepository userRepository;

    @Autowired
    ReportService reportService;

    private final User user = new User(1L, "mock", "mockMail", "mockPassword", 0L, "mock", new HashSet<>(),new HashSet<>());
    private final ReportResponse reportResponse = new ReportResponse(1L,"mock",  new Date(), "mock", "mock", (float) 1.0001, (float) 1.0001, user, false, "mock", EReport.DESEURI.name(), "mock", 0);
    private final Report report = new Report(1L, "mock", reportResponse.getDate(), "mock", "mock", (float) 1.0001, (float) 1.0001, user, false, "mock", EReport.DESEURI, "mock", 0);

    @Test
    public void testValidateSuccess() throws AlreadyReportedInThatArea {
        // given
        Report reportFromTheSameDay = new Report("mock", "mock", "mock", (float) 11.0000, (float) 11.0000, user, false, "mock", EReport.DESEURI.name(), "mock", 0);
        List<Report> reportList = new ArrayList<>();
        reportList.add(report);
        when(reportRepository.findByUserId(anyLong())).thenReturn(Optional.of(reportList));

        // when
        boolean result = reportService.validate(reportFromTheSameDay);

        // then
        assertEquals(true, result);
    }

    @Test
    public void testValidateNoReportsFromUser() throws AlreadyReportedInThatArea {
        // given
        List<Report> reportList = new ArrayList<>();
        reportList.add(report);
        when(reportRepository.findByUserId(anyLong())).thenReturn(Optional.empty());

        // when
        boolean result = reportService.validate(report);

        // then
        assertEquals(true, result);
    }

    @Test
    public void testValidateAlreadyReported() {
        // given
        Report reportFromTheSameDay = new Report("mock", "mock", "mock", (float) 1.00012, (float) 1.00012, user, false, "mock", EReport.DESEURI.name(), "mock", 0);
        List<Report> reportList = new ArrayList<>();
        reportList.add(report);
        when(reportRepository.findByUserId(anyLong())).thenReturn(Optional.of(reportList));

        // when
        assertThrows(AlreadyReportedInThatArea.class, () -> reportService.validate(reportFromTheSameDay));
    }

    @Test
    public void testDelete() {
        // given
        when(reportRepository.findById(anyLong())).thenReturn(Optional.of(report));
        doNothing().when(reportRepository).deleteById(any());
        // when
        reportService.deleteReport(1);

        // then
        verify(reportRepository, times(1)).deleteById(anyLong());
    }


    @Test
    public void testCreate() throws AlreadyReportedInThatArea {
        // given
        when(userRepository.findByUsername("mock")).thenReturn(Optional.of(user));
        when(reportRepository.findByUserId(anyLong())).thenReturn(Optional.empty());
        when(reportRepository.save(any())).thenReturn(report);
        when(userRepository.save(any(User.class))).thenReturn(user);
        ReportRequest reportRequest = new ReportRequest("","","", (float) 0, (float) 0,"mock",true,"",EReport.DESEURI.name(), "",1);
        // when
        reportService.createReport(reportRequest);

        // then
        verify(reportRepository, times(1)).save(any(Report.class));
    }

    @Test
    public void testCreateInvalidUser() {
        // given
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
        when(reportRepository.findByUserId(anyLong())).thenReturn(Optional.empty());
        when(reportRepository.save(any())).thenReturn(report);
        when(userRepository.save(any(User.class))).thenReturn(user);

        // when -> then
        assertThrows(IllegalArgumentException.class, () -> reportService.createReport(new ReportRequest()));
        verify(reportRepository, times(0)).save(any());
    }

    @Test
    public void testGetReport() {
        // given
        when(reportRepository.findById(anyLong())).thenReturn(Optional.of(report));
        // when -> then
        assertEquals(report, reportService.getReport(1L));
    }

    @Test
    public void testGetReportNotFound() {
        // given
        when(reportRepository.findById(anyLong())).thenReturn(Optional.empty());
        // when -> then
        assertThrows(IllegalArgumentException.class, () -> reportService.getReport(1L));
    }

    @Test
    public void testGetReports()  {
        // given
        Date date = new Date();
        List<Report> reportList = new ArrayList<>();
        report.setDate(date);
        reportList.add(report);
        List<ReportResponse> reportResponses = new ArrayList<>();
        reportResponse.setDate(date);
        reportResponses.add(reportResponse);
        when(reportRepository.findAll()).thenReturn(reportList);

        // when -> then
        List<ReportResponse> reports = reportService.getReports();
        reports.get(0).setDate(date);
        assertEquals(reportResponses, reports);
    }

    @Test
    public void testGetReportsFromUser()  {
        // given
        Date date = new Date();
        List<Report> reportList = new ArrayList<>();
        report.setDate(date);
        reportList.add(report);
        List<ReportResponse> reportResponses = new ArrayList<>();
        reportResponse.setDate(date);
        reportResponses.add(reportResponse);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(reportRepository.findByUser(any(User.class))).thenReturn(Optional.of(reportList));
        // when -> then
        List<ReportResponse> reportsFromUser = reportService.getReportsFromUser(1L);
        reportsFromUser.get(0).setDate(date);
        assertEquals(reportResponses, reportsFromUser);
    }
}
